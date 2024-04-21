package no.ntnu.idatt1005.foodi.controller.pages;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.DAO.ShoppingListDAO;
import no.ntnu.idatt1005.foodi.model.objects.dtos.AmountedIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.ExpiringIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.PartiallyRemovedAmountedIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.RecipeWithPartiallyRemovedIngredients;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;
import no.ntnu.idatt1005.foodi.view.components.shoppinglist.AddIngredientsExpirationDialog;
import no.ntnu.idatt1005.foodi.view.views.ShoppingList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Controller for the inventory page. This controller manages the updates to the inventory page.
 *
 * @author Henrik Kvamme
 * @version 1.0
 */
public class ShoppingListController extends PageController {

  private static final Logger LOGGER = Logger.getLogger(ShoppingListController.class.getName());
  private final SimpleObjectProperty<User> currentUserProperty;
  private final ShoppingList view;
  private final ShoppingListDAO shoppingListDAO = new ShoppingListDAO();
  private final IngredientDAO ingredientDAO = new IngredientDAO();

  /**
   * Constructor for the InventoryController class.
   *
   * @param inventoryPage the inventory view
   */
  public ShoppingListController(
      ShoppingList shoppingListPage,
      SimpleObjectProperty<User> currentUserProperty
  ) {
    super(shoppingListPage);
    this.view = shoppingListPage;

    this.currentUserProperty = currentUserProperty;

    attachToView();
    update();
  }

  private void attachToView() {
    view.setOnAddItems(this::addShoppingListToInventory);
    view.setOnClearItems(this::clearShoppingList);
  }

  @Override
  void update() {
    view.render(getAccountedRecipes());
  }

  private void addShoppingListToInventory() {
    List<AmountedIngredient> ingredients = getNeededIngredients();

    if (ingredients == null || ingredients.isEmpty()) {
      return;
    }
    AddIngredientsExpirationDialog dialog = new AddIngredientsExpirationDialog(ingredients);

    dialog.setOnCloseRequest(e -> {
      if (!dialog.isFilled() || dialog.getExpiringIngredients() == null) {
        return;
      }
      for (ExpiringIngredient ingredient : dialog.getExpiringIngredients()) {
        if (ingredient == null) {
          continue;
        }
        ingredientDAO.saveIngredientToUserInventory(
            currentUserProperty.get().userId(),
            ingredient.getName(),
            ingredient.getUnit(),
            ingredient.getCategory(),
            ingredient.getAmount(),
            new java.sql.Date(ingredient.getExpirationDateAsDate().getTime())
        );
      }
      clearShoppingList();
      update();
    });
    dialog.show();
  }

  private void clearShoppingList() {
    LOGGER.severe("Not implemented.");
  }

  /**
   * Retrieves the saved recipes for the current user and subtracts the amount of items the user
   * currently has.
   *
   * @return a list of recipes with partially removed ingredients
   */
  private @NotNull List<RecipeWithPartiallyRemovedIngredients> getAccountedRecipes() {
    HashMap<Integer, RecipeWithPartiallyRemovedIngredients> accountedRecipes = new HashMap<>();
    List<AmountedIngredient> totalIngredients = getNeededIngredients();

    if (totalIngredients == null) {
      return new ArrayList<>();
    }
    if (true) {
      return new ArrayList<>();
    }

    List<RecipeWithPartiallyRemovedIngredients> recipes =
        new ArrayList<>();
//        shoppingListDAO
//        .getRecipesInShoppingListForUser(
//            currentUserProperty.get().userId()
//        );

    for (AmountedIngredient ingredient : totalIngredients) {

      for (RecipeWithPartiallyRemovedIngredients recipe : recipes) {
        boolean recipeContainsIngredient = recipe
            .getIngredients()
            .stream()
            .anyMatch(i -> i.getId() == ingredient.getId());
        if (!recipeContainsIngredient) {
          continue;
        }
        if (!accountedRecipes.containsKey(recipe.getId())) {
          accountedRecipes.put(
              recipe.getId(),
              recipe
          );
          continue;
        }
        PartiallyRemovedAmountedIngredient matchingIngredient = accountedRecipes
            .get(recipe.getId())
            .getIngredients()
            .stream()
            .filter(i -> i.getId() == ingredient.getId())
            .findAny()
            .orElse(null);
        if (matchingIngredient == null) {
          continue;
        }
        double transferredAmount = Math.min(
            matchingIngredient.getRemainingAmount(),
            ingredient.getAmount()
        );
        matchingIngredient.setRemovedAmount(
            matchingIngredient.getRemovedAmount() + transferredAmount
        );
        ingredient.setAmount(ingredient.getAmount() - transferredAmount);
      }
    }

    return new ArrayList<>(accountedRecipes.values());
  }

  /**
   * Retrieves the ingredients the user has in their inventory.
   *
   * @return a list of ingredients the user has in their inventory
   */
  private @Nullable List<AmountedIngredient> getNeededIngredients() {
    List<ExpiringIngredient> fetchedIngredients = ingredientDAO
        .retrieveExpiringIngredientsFromInventory(currentUserProperty.get().userId());

    if (fetchedIngredients == null) {
      return null;
    }

    ArrayList<AmountedIngredient> compiledIngredients = new ArrayList<>();

    for (AmountedIngredient ingredient : fetchedIngredients) {
      AmountedIngredient value = compiledIngredients
          .stream()
          .filter(i -> i.getId() == ingredient.getId())
          .findAny()
          .orElse(null);
      if (value == null) {
        compiledIngredients.add(ingredient);
        continue;
      }
      value.setAmount(value.getAmount() + ingredient.getAmount());
    }
    return compiledIngredients;
  }
}
