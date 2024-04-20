package no.ntnu.idatt1005.foodi.model.DAO;

import static no.ntnu.idatt1005.foodi.model.repository.Main.Database.DB_URL;
import static no.ntnu.idatt1005.foodi.model.repository.Main.Database.PASS;
import static no.ntnu.idatt1005.foodi.model.repository.Main.Database.USER;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Logger;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A helper class for building SQL queries.
 * <pre>
 * Ingredient i =  new QueryBuilder("SELECT * FROM ingredient WHERE id = ?")
 *         .addInt(5) // the id of the ingredient
 *         .executeQuerySafe(rs -> {
 *           if (rs.next()) {
 *             // code to create an ingredient object
 *             return new Ingredient(...);
 *           }
 *           return null;
 *         });
 * </pre>
 *
 * @author Leif Mørstad
 * @version 1.0
 */
class QueryBuilder {

  private static final Logger LOGGER = Logger.getLogger(QueryBuilder.class.getName());
  /**
   * The SQL query to use and build from.
   */
  private final String query;
  /**
   * A list of methods to modify the statement.
   */
  private final ArrayList<StatementModifier> parts = new ArrayList<>();
  /**
   * Whether the query is locked or not. After the query is run, the statement is locked and the
   * query parameters can no longer be modified.
   */
  private boolean locked = false;

  /**
   * Creates a new QueryBuilder with the given SQL query, with "?" as parameters.
   *
   * @param query The SQL query to build from.
   */
  public QueryBuilder(@Language("SQL") String query) {
    this.query = query;
  }

  /**
   * Replaces the next "?" parameter with the given integer.
   *
   * @param value The integer to use.
   * @return This QueryBuilder instance.
   */
  public @NotNull QueryBuilder addInt(int value) {
    checkLocked();
    parts.add((i, statement) -> statement.setInt(i, value));
    return this;
  }

  private void checkLocked() {
    if (locked) {
      throw new IllegalStateException("Query is locked and cannot be modified.");
    }
  }

  /**
   * Replaces the next "?" parameter with the given string.
   *
   * @param value The string to use.
   * @return This QueryBuilder instance.
   */
  public @NotNull QueryBuilder addString(@NotNull String value) {
    checkLocked();
    parts.add((i, statement) -> statement.setString(i, value));
    return this;
  }

  /**
   * Replaces the next "?" parameter with the given double.
   *
   * @param value The double to use.
   * @return This QueryBuilder instance.
   */
  public @NotNull QueryBuilder addDouble(@NotNull Double value) {
    checkLocked();
    parts.add((i, statement) -> statement.setDouble(i, value));
    return this;
  }

  /**
   * Replaces the next "?" parameter with the given date.
   *
   * @param value The date to use.
   * @return This QueryBuilder instance.
   */
  public @NotNull QueryBuilder addDate(@Nullable Date value) {
    checkLocked();
    parts.add((i, statement) -> statement.setDate(i, value));
    return this;
  }

  /**
   * Replaces the next "?" parameter with the given boolean.
   *
   * @param value The boolean to use.
   * @return This QueryBuilder instance.
   */
  public @NotNull QueryBuilder addBoolean(boolean value) {
    checkLocked();
    parts.add((i, statement) -> statement.setBoolean(i, value));
    return this;
  }

  /**
   * Executes a SELECT query and returns the result without throwing if it encounters an error.
   * Returns {@code null} if it does.
   *
   * @return The result of the query. Returns null if the query throws.
   * @see PreparedStatement#executeQuery()
   * @see #executeQuery(ResultHandler)
   */
  public <T> @Nullable T executeQuerySafe(@NotNull ResultHandler<ResultSet, T> handler) {
    try {
      return executeQuery(handler);
    } catch (SQLException e) {
      LOGGER.info("Error while executing query: " + e.getMessage());
      return null;
    }
  }

  // TODO: Add more add methods for different types of parameters if needed.

  /**
   * Executes a SELECT query and returns the result through a handler function.
   *
   * @return The result of the query.
   * @throws SQLException If an error occurs while executing the query.
   * @see PreparedStatement#executeQuery()
   */
  public <T> T executeQuery(@NotNull ResultHandler<ResultSet, T> handler) throws SQLException {
    return runAndThenReturnWithMethod(PreparedStatement::executeQuery, handler);
  }

  /**
   * Runs the given method on the query and returns the result. Running it locks the query. The
   * handler param should not return {@link ResultSet}, as this <i>might</i> cause a resource leak,
   * but if needed for a hack remember to {@link ResultSet#close()} it after use.
   *
   * @param method       The method to run.
   * @param handler      The handler to handle the result.
   * @param <StatementT> The return type of the statement.
   * @param <ReturnT>    The return type of the method.
   * @throws SQLException If an error occurs while running the query.
   */
  private <StatementT, ReturnT> ReturnT runAndThenReturnWithMethod(
      @NotNull StatementMethod<StatementT> method,
      @NotNull ResultHandler<StatementT, ReturnT> handler
  ) throws SQLException {
    locked = true;

    // Store the result of the query outside the try-with-resources block so that the connection
    // is ensured to close.
    ReturnT result;
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement statement = connection.prepareStatement(query)) {
      // Throw an exception if the number of parameters does not match the number of parts.
      if (statement.getParameterMetaData().getParameterCount() != parts.size()) {
        throw new IllegalStateException(
            "Incorrect number of parameters. Got "
                + parts.size()
                + ", expected "
                + statement.getParameterMetaData().getParameterCount()
                + "."
        );
      }
      // Modify the statement with all the modifier functions.
      for (int i = 0; i < parts.size(); i++) {
        parts
            .get(i)
            .modifyStatement(i + 1, statement);
      }
      // Run the method and handle the result.
      StatementT statementResult = method.runMethod(statement);
      result = handler.handleResult(statementResult);
    }
    return result;
  }

  /**
   * Executes a SELECT query and consumes the value without throwing if it encounters an error.
   *
   * @see PreparedStatement#executeQuery()
   * @see #executeQuery(ResultHandler)
   */
  public void executeQuerySafe(@NotNull ThrowingConsumer<ResultSet> handler) {
    try {
      executeQuery(handler);
    } catch (SQLException e) {
      LOGGER.info("Error while executing query: " + e.getMessage());
    }
  }

  /**
   * Executes a SELECT query.
   *
   * @param handler The handler to handle the result.
   * @throws SQLException If an error occurs while executing the query.
   * @see PreparedStatement#executeQuery()
   */
  public void executeQuery(@NotNull ThrowingConsumer<ResultSet> handler) throws SQLException {
    runAndThenReturnWithMethod(PreparedStatement::executeQuery, convertToConsumer(handler));
  }

  /**
   * Converts a {@link ThrowingConsumer} to a {@link ResultHandler} with a null return.
   *
   * @param consumer The consumer to convert.
   * @param <T>      The return type of the consumer.
   * @return The converted consumer.
   */
  private <T> @NotNull ResultHandler<T, Void> convertToConsumer(
      @NotNull ThrowingConsumer<T> consumer
  ) {
    return e -> {
      consumer.accept(e);
      return null;
    };
  }

  /**
   * Executes a SELECT query and consumes the value without throwing if it encounters an error.
   *
   * @see PreparedStatement#executeQuery()
   * @see #executeQuery(ResultHandler)
   */
  public void executeQuerySafe() {
    try {
      executeQuery();
    } catch (SQLException e) {
      LOGGER.info("Error while executing query: " + e.getMessage());
    }
  }

  /**
   * Executes a SELECT query.
   *
   * @throws SQLException If an error occurs while executing the query.
   * @see PreparedStatement#executeQuery()
   */
  public void executeQuery() throws SQLException {
    runAndThenReturnWithMethod(PreparedStatement::executeQuery, e -> null);
  }

  /**
   * Executes either a INSERT, UPDATE or a DELETE query without throwing if it encounters an error.
   * Returns {@code null} if it does.
   *
   * @return The result of the query. Returns null if the query throws.
   * @see PreparedStatement#executeUpdate()
   * @see #executeUpdate(ResultHandler)
   */
  public <T> @Nullable T executeUpdateSafe(@NotNull ResultHandler<Integer, T> handler) {
    try {
      return executeUpdate(handler);
    } catch (SQLException e) {
      return null;
    }
  }

  /**
   * Executes either a INSERT, UPDATE or a DELETE query.
   *
   * @return The result of the query.
   * @throws SQLException If an error occurs while executing the query.
   * @see PreparedStatement#executeUpdate()
   */
  public <T> T executeUpdate(@NotNull ResultHandler<Integer, T> handler) throws SQLException {
    return runAndThenReturnWithMethod(PreparedStatement::executeUpdate, handler);
  }

  /**
   * Executes either a INSERT, UPDATE or a DELETE query and consumes the value without throwing if
   * it encounters an error.
   *
   * @see PreparedStatement#executeUpdate()
   * @see #executeUpdate(ResultHandler)
   */
  public void executeUpdateSafe(@NotNull ThrowingConsumer<Integer> handler) {
    try {
      executeUpdate(handler);
    } catch (SQLException e) {
      LOGGER.info("Error while executing query: " + e.getMessage());
    }
  }

  /**
   * Executes either a INSERT, UPDATE or a DELETE query.
   *
   * @param handler The handler to handle the result.
   * @throws SQLException If an error occurs while executing the update.
   * @see PreparedStatement#executeQuery()
   */
  public void executeUpdate(@NotNull ThrowingConsumer<Integer> handler) throws SQLException {
    runAndThenReturnWithMethod(PreparedStatement::executeUpdate, convertToConsumer(handler));
  }

  /**
   * Executes either a INSERT, UPDATE or a DELETE query without throwing if it encounters an error.
   *
   * @see PreparedStatement#executeUpdate()
   * @see #executeUpdate(ResultHandler)
   */
  public void executeUpdateSafe() {
    try {
      executeUpdate();
    } catch (SQLException e) {
      LOGGER.info("Error while executing query: " + e.getMessage());
    }
  }

  /**
   * Executes either a INSERT, UPDATE or a DELETE query.
   *
   * @throws SQLException If an error occurs while executing the query.
   * @see PreparedStatement#executeUpdate()
   */
  public void executeUpdate() throws SQLException {
    runAndThenReturnWithMethod(PreparedStatement::executeUpdate, e -> null);
  }

  /**
   * A functional interface for modifying a {@link PreparedStatement} parameter at a given index.
   * Index starts at 1.
   */
  public interface StatementModifier {

    /**
     * Modifies the statement at the given index.
     *
     * @param modifyingIndex The index to modify starting at 1.
     * @param statement      The statement to modify.
     * @throws SQLException If an error occurs while modifying the statement.
     */
    void modifyStatement(int modifyingIndex, PreparedStatement statement) throws SQLException;
  }

  /**
   * Represents a method that can be run on a {@link PreparedStatement}.
   *
   * @param <T> The return type of the method.
   */
  public interface StatementMethod<T> {

    /**
     * Runs the method on the given statement.
     *
     * @param statement The statement to run the method on.
     * @return The return value of the method.
     * @throws SQLException If an error occurs while running the method.
     */
    T runMethod(@NotNull PreparedStatement statement) throws SQLException;
  }

  /**
   * Runs the given method on the query and returns the result.
   *
   * @param <InputT>   The given type of the method.
   * @param <ResultT>> The return type of the method.
   */
  public interface ResultHandler<InputT, ResultT> {

    /**
     * Handles the result of the query.
     *
     * @param resultSet The result to handle.
     * @return The result of the handler.
     * @throws SQLException If an error occurs while handling the result.
     */
    ResultT handleResult(InputT resultSet) throws SQLException;
  }

  /**
   * A functional interface for consuming a value and throwing an exception.
   *
   * @param <T> The type of the value to consume.
   */
  public interface ThrowingConsumer<T> {

    /**
     * Consumes the value.
     *
     * @param t The value to consume.
     * @throws SQLException If an error occurs while consuming the value.
     */
    void accept(T t) throws SQLException;
  }
}