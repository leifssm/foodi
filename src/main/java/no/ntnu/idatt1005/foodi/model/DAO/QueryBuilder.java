package no.ntnu.idatt1005.foodi.model.DAO;

import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.ArrayList;

import static no.ntnu.idatt1005.foodi.model.repository.Database.*;

/**
 * A helper class for building SQL queries.
 *
 * @version 1.0
 * @author Leif Mørstad
 */
class QueryBuilder {
  /**
   * A functional interface for modifying a {@link PreparedStatement} parameter at a given index. Index
   * starts at 1.
   */
  public interface StatementModifier {
    /**
     * Modifies the statement at the given index.
     *
     * @param modifyingIndex The index to modify starting at 1.
     * @param statement The statement to modify.
     * @throws SQLException If an error occurs while modifying the statement.
     */
    void modifyStatement(int modifyingIndex, PreparedStatement statement) throws SQLException;
  }

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
  public QueryBuilder(String query) {
    this.query = query;
  }

  private void checkLocked() {
    if (locked) {
      throw new IllegalStateException("Query is locked and cannot be modified.");
    }
  }

  /**
   * Replaces the next "?" parameter with the given integer.
   *
   * @param value The integer to use.
   * @return This QueryBuilder instance.
   */
  public QueryBuilder addInt(int value) {
    checkLocked();
    parts.add((i, statement) -> statement.setInt(i, value));
    return this;
  }

  /**
   * Replaces the next "?" parameter with the given string.
   *
   * @param value The string to use.
   * @return This QueryBuilder instance.
   */
  public QueryBuilder addString(String value) {
    checkLocked();
    parts.add((i, statement) -> statement.setString(i, value));
    return this;
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
    T runMethod(PreparedStatement statement) throws SQLException;
  }

  /**
   * Runs the given method on the query and returns the result. Running it locks the query.
   * @param method
   * @return
   * @param <T>
   */
  private <T> @Nullable T runAndThenReturnWithMethod(StatementMethod<T> method) {
    locked = true;
    try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
         PreparedStatement statement = connection.prepareStatement(query)) {
      if (statement.getParameterMetaData().getParameterCount() != parts.size()) {
        throw new IllegalStateException(
            "Incorrect number of parameters. Got "
                + parts.size()
                + ", expected "
                + statement.getParameterMetaData().getParameterCount()
                + "."
        );
      }
      for (int i = 0; i < parts.size(); i++) {
        parts
            .get(i)
            .modifyStatement(i + 1, statement);
      }
      return method.runMethod(statement);
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Executes the query and returns the result.
   *
   * @return The result of the query. Returns null if an error occurs.
   * @see PreparedStatement#executeQuery()
   */
  public @Nullable ResultSet executeQuery() {
    return runAndThenReturnWithMethod(PreparedStatement::executeQuery);
  }

  /**
   * Executes the update query.
   *
   * @return The result of the query. Returns null if an error occurs.
   * @see PreparedStatement#executeUpdate()
   */
  public @Nullable Integer executeUpdate() {
    return runAndThenReturnWithMethod(PreparedStatement::executeUpdate);
  }
}