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
  private List<RecipeWithPartiallyRemovedIngredients> recipes;

  /**
   * Constructor for the InventoryController class.
   *
   * @param shoppingListPage    the view to be controlled
   * @param currentUserProperty the current user
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
    recipes = getAccountedRecipes();

    List<Runnable> onRemoveMethods = new ArrayList<>();
    for (RecipeWithPartiallyRemovedIngredients recipe : recipes) {
      onRemoveMethods.add(() -> {
        shoppingListDAO.deleteRecipe(
            currentUserProperty.get().userId(),
            recipe.getId()
        );
        update();
      });
    }

    view.render(recipes, onRemoveMethods);
  }

  private void addShoppingListToInventory() {
    List<AmountedIngredient> ingredients = getIngredientsFromCurrentRecipes();

    if (ingredients.isEmpty()) {
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
    });
    dialog.show();
  }

  private void clearShoppingList() {
    shoppingListDAO.clearShoppingList(currentUserProperty.get().userId());
    update();
  }

  /**
   * Retrieves the saved recipes for the current user and subtracts the amount of items the user
   * currently has.
   *
   * @return a list of recipes with partially removed ingredients
   */
  private @NotNull List<RecipeWithPartiallyRemovedIngredients> getAccountedRecipes() {
    List<AmountedIngredient> totalIngredients = getCurrentIngredients();

    HashMap<Integer, RecipeWithPartiallyRemovedIngredients> accountedRecipes = new HashMap<>();
    List<RecipeWithPartiallyRemovedIngredients> recipes = shoppingListDAO.getRecipesWithIngredients(
        currentUserProperty.get().userId()
    );

    for (RecipeWithPartiallyRemovedIngredients recipe : recipes) {
      accountedRecipes.putIfAbsent(
          recipe.getId(),
          recipe
      );
      for (AmountedIngredient ingredient : totalIngredients) {
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
  private @NotNull List<AmountedIngredient> getCurrentIngredients() {
    List<ExpiringIngredient> fetchedIngredients = ingredientDAO
        .retrieveExpiringIngredientsFromInventory(currentUserProperty.get().userId());

    if (fetchedIngredients == null) {
      return new ArrayList<>();
    }

    ArrayList<AmountedIngredient> compiledIngredients = new ArrayList<>();

    System.out.println(fetchedIngredients.size());
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

  private List<AmountedIngredient> getIngredientsFromCurrentRecipes() {
    HashMap<AmountedIngredient, Double> ingredients = new HashMap<>();
    for (RecipeWithPartiallyRemovedIngredients recipe : recipes) {
      for (PartiallyRemovedAmountedIngredient ingredient : recipe.getIngredients()) {
        ingredients.computeIfPresent(
            ingredient,
            (key, value) -> value + ingredient.getRemainingAmount()
        );
        ingredients.putIfAbsent(ingredient, ingredient.getRemainingAmount());
      }
    }
    return ingredients
        .entrySet()
        .stream()
        .map(entry -> new AmountedIngredient(entry.getKey(), entry.getValue()))
        .toList();
  }
}
