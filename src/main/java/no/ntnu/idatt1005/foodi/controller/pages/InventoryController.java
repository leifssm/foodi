package no.ntnu.idatt1005.foodi.controller.pages;

import static java.time.temporal.ChronoUnit.DAYS;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.objects.dtos.ExpiringIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.GroupedExpiringIngredients;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.views.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * Controller for the inventory page. This controller manages the updates to the inventory page.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public class InventoryController extends PageController {

  private static final Logger LOGGER = Logger.getLogger(InventoryController.class.getName());
  private final SimpleObjectProperty<User> currentUserProperty;
  private final Inventory view;
  private final IngredientDAO ingredientDAO;

  /**
   * Constructor for the InventoryController class.
   *
   * @param inventoryPage the inventory view
   */
  public InventoryController(Inventory inventoryPage,
      SimpleObjectProperty<User> currentUserProperty) {
    super(inventoryPage);
    this.view = inventoryPage;

    this.currentUserProperty = currentUserProperty;
    this.ingredientDAO = new IngredientDAO();

    attachToView();
    update();
  }

  private void attachToView() {
    view.setOnAddItem(this::onAddItem);
    view.setOnFreezeItems(this::onFreezeItem);
    view.setOnDeleteItems(this::onDeleteItems);
    view.setOnAmountChange(this::onAmountChange);
  }

  /**
   * Updates the amount of an ingredient in the inventory. If the amount is 0, the ingredient is
   * removed.
   *
   * @param ingredient the ingredient to update
   */
  private void onAmountChange(ExpiringIngredient ingredient) {
    if (ingredient.getAmount() == 0) {
      deleteItem(ingredient);
      update();
      return;
    }

    ingredientDAO.updateIngredientInUserInventory(
        currentUserProperty.get().userId(),
        ingredient.getInventoryId(),
        ingredient.getAmount(),
        ingredient.getExpirationDate()
    );

    update();
  }

  /**
   * Adds an ingredient to the inventory with {@link IngredientDAO}.
   *
   * @param ingredient the ingredient to add
   */
  private void onAddItem(ExpiringIngredient ingredient) {
    try {
      ingredientDAO.saveIngredientToUserInventory(
          currentUserProperty.get().userId(),
          ingredient.getName(),
          ingredient.getUnit(),
          ingredient.getCategory(),
          ingredient.getAmount(),
          new java.sql.Date(ingredient.getExpirationDateAsDate().getTime())
      );
    } catch (SQLException e) {
      LOGGER.severe("Failed to add ingredient to inventory: " + e.getMessage());
    }

    update();
  }

  /**
   * Returns the date a frozen ingredient should be eaten before.
   *
   * @param expirationDate the expiration date of the ingredient
   * @return the date the ingredient should be eaten before
   */
  private LocalDate getFrozenDate(@NotNull LocalDate expirationDate) {
    long daysUntilExpiration = DAYS.between(LocalDate.now(), expirationDate);
    return LocalDate.now().plusDays(5 * daysUntilExpiration);
  }

  /**
   * Returns the date an unfrozen ingredient should be eaten before.
   *
   * @param expirationDate the expiration date of the ingredient
   * @return the date the ingredient should be eaten before
   */
  private LocalDate getUnfrozenDate(@NotNull LocalDate expirationDate) {
    // expiration date is an unused parameter for future compatibility
    return LocalDate.now().plusDays(2);
  }

  private void onFreezeItem() {
    List<ExpiringIngredient> ingredients = view.getSelectedItems();
    LOGGER.info("Toggling freeze on " + ingredients.size() + " items");
    for (ExpiringIngredient ingredient : ingredients) {
      ingredientDAO.toggleFreezeIngredient(
          currentUserProperty.get().userId(),
          ingredient.getId(),
          !ingredient.getIsFrozen()
      );

      LocalDate newExpirationDate = ingredient.getIsFrozen()
          ? getUnfrozenDate(ingredient.getExpirationDate())
          : getFrozenDate(ingredient.getExpirationDate());

      ingredientDAO.updateIngredientExpirationDate(
          currentUserProperty.get().userId(),
          ingredient.getId(),
          newExpirationDate
      );
    }

    update();
  }

  private void onDeleteItems() {
    List<ExpiringIngredient> ingredients = view.getSelectedItems();
    LOGGER.info("Deleting " + ingredients.size() + " items");

    for (ExpiringIngredient ingredient : ingredients) {
      deleteItem(ingredient);
    }
    update();
  }

  /**
   * Deletes an ingredient from the inventory.
   *
   * @param ingredient the ingredient to delete
   */
  private void deleteItem(ExpiringIngredient ingredient) {
    ingredientDAO.deleteIngredientFromUserInventory(
        currentUserProperty.get().userId(),
        ingredient.getInventoryId()
    );
    update();
  }

  @Override
  void update() {
    view.render(getInventoryDataFromUser());
  }

  /**
   * Fetches the inventory {@link ExpiringIngredient} from the user and groups it by
   * {@link GroupedExpiringIngredients}.
   *
   * @return a list of grouped expiring ingredients
   */
  private @NotNull List<GroupedExpiringIngredients> getInventoryDataFromUser() {
    List<ExpiringIngredient> inventoryData = ingredientDAO.retrieveExpiringIngredientsFromInventory(
        currentUserProperty.get().userId()
    );

    if (inventoryData == null) {
      return new ArrayList<>();
    }

    // Group the ingredients by name
    HashMap<String, ArrayList<ExpiringIngredient>> groupedInventoryData = new HashMap<>();
    for (ExpiringIngredient ingredient : inventoryData) {
      String name = ingredient.getName();
      if (groupedInventoryData.containsKey(name)) {
        groupedInventoryData.get(name).add(ingredient);
      } else {
        groupedInventoryData.put(name, new ArrayList<>(List.of(ingredient)));
      }
    }

    return groupedInventoryData
        .entrySet()
        .stream()
        .map(entry ->
            new GroupedExpiringIngredients(entry.getKey(), entry.getValue())
        )
        .toList();
  }
}
