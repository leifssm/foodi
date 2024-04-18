package no.ntnu.idatt1005.foodi.controller.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.objects.dtos.ExpiringIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.GroupedExpiringIngredients;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.views.Inventory;
import org.jetbrains.annotations.NotNull;

/**
 * Controller for the inventory page. This controller manages the updates to the inventory page.
 */
public class InventoryController extends PageController {

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
  }

  @Override
  void update() {
    var inventoryData = getInventoryDataFromUser();
    view.render(inventoryData);
  }

  /**
   * Adds an ingredient to the inventory with the inventory DAO.
   *
   * @param ingredient the ingredient to add
   */
  private void onAddItem(ExpiringIngredient ingredient) {
    System.out.println("Adding ingredient: " + ingredient.getName());
    try {
      ingredientDAO.saveIngredientToUserInventory(
          currentUserProperty.get().userId(),
          ingredient.getName(),
          ingredient.getUnit(),
          ingredient.getCategory(),
          ingredient.getAmount(),
          new java.sql.Date(ingredient.getExpirationDateAsDate().getTime())
      );
    } catch (Exception e) {
      e.printStackTrace();
    }

    update();
  }

  /**
   * Fetches the inventory data from the user.
   *
   * @return the inventory data from the user
   */
  private @NotNull List<GroupedExpiringIngredients> getInventoryDataFromUser() {
    List<ExpiringIngredient> inventoryData = ingredientDAO.retrieveExpiringIngredientsFromInventory(
        currentUserProperty.get().userId());

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

    return groupedInventoryData.entrySet()
        .stream()
        .map(entry -> new GroupedExpiringIngredients(entry.getKey(), entry.getValue()))
        .toList();
  }
}
