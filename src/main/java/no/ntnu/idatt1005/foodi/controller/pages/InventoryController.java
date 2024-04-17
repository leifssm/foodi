package no.ntnu.idatt1005.foodi.controller.pages;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import no.ntnu.idatt1005.foodi.model.objects.dtos.ExpiringIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.GroupedExpiringIngredients;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient.Category;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient.Unit;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.views.Inventory;

/**
 * Controller for the inventory page. This controller manages the updates to the inventory page.
 */
public class InventoryController extends PageController {

  private final SimpleObjectProperty<User> currentUserProperty;
  private final Inventory view;
  private ArrayList<GroupedExpiringIngredients> dummyInventoryData;

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

    initializeDummyData();
    attachToView();
    update();
  }

  private void initializeDummyData() {
    dummyInventoryData = new ArrayList<>(List.of(
        new GroupedExpiringIngredients("Milk", List.of(
            new ExpiringIngredient(1, "Milk", Unit.LITER, Category.DAIRY, 1.5,
                LocalDate.of(2024, 5, 1)),
            new ExpiringIngredient(2, "Milk", Unit.LITER, Category.DAIRY, .5,
                LocalDate.of(2024, 4, 15))
        )),
        new GroupedExpiringIngredients("Cheese", List.of(
            new ExpiringIngredient(3, "Cheese", Unit.KILOGRAM, Category.DAIRY, 0.5,
                LocalDate.of(2024, 4, 18))
        )),
        new GroupedExpiringIngredients("Bread", List.of(
            new ExpiringIngredient(4, "Bread", Unit.PIECE, Category.GRAIN, 1,
                LocalDate.of(2024, 5, 1))
        ))
    ));
  }

  private void attachToView() {
    view.setOnAddItem(this::onAddItem);
  }

  @Override
  void update() {
    var inventoryData = getInventoryDataFromUser(currentUserProperty.get());
    view.render(inventoryData);
  }

  /**
   * Adds an ingredient to the inventory with the inventory DAO.
   *
   * @param ingredient the ingredient to add
   */
  private void onAddItem(ExpiringIngredient ingredient) {
    System.out.println("Adding ingredient: " + ingredient.getName());
    dummyInventoryData.add(
        new GroupedExpiringIngredients(ingredient.getName(), List.of(ingredient)));
    
    update();
  }

  /**
   * Fetches the inventory data from the user. Should be replaced with a call to the inventory DAO.
   *
   * @param user the user to fetch the inventory data from
   * @return the inventory data from the user
   */
  private List<GroupedExpiringIngredients> getInventoryDataFromUser(User user) {
    System.out.println("Fetching inventory data from user: " + user.name());
    return dummyInventoryData;
  }
}
