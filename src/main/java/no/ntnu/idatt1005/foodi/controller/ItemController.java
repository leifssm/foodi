package no.ntnu.idatt1005.foodi.controller;

import java.sql.SQLException;
import java.util.Date;
import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.DAO.InventoryIngredientDAO;
import no.ntnu.idatt1005.foodi.model.DAO.UserDAO;
import no.ntnu.idatt1005.foodi.model.objects.Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.IngredientCategory;
import no.ntnu.idatt1005.foodi.model.objects.IngredientUnit;
import no.ntnu.idatt1005.foodi.model.objects.InventoryIngredient;
import no.ntnu.idatt1005.foodi.model.objects.User;

/**
 * This class is responsible for handling the usage of items in the inventory back connecting
 * Ingredient, User and Inventory Classes
 *
 * @author Kevin Dennis Mazali
 * @version 0.2.0
 */

public class ItemController {

  private final IngredientDAO ingredientDAO = new IngredientDAO();
  private final UserDAO userDAO;
  private final InventoryIngredientDAO inventoryIngredientDAO = new InventoryIngredientDAO();

  public ItemController() {
    userDAO = new UserDAO();
  }

  public void saveItem(String inputName, IngredientCategory inputCategory, IngredientUnit inputUnit,
      int inputAmount, Date inputExpirationDate) {
    final int newIngredientId = inventoryIngredientDAO.countInventoryItems() + 1;
    System.out.println("IngredientIdFillerValue: " + newIngredientId);

    try {
      Ingredient.IngredientCategory ingredientCategory = Ingredient.IngredientCategory.valueOf(
          inputCategory.name());
      Ingredient.IngredientUnit ingredientUnit = Ingredient.IngredientUnit.valueOf(
          inputUnit.name());

      Ingredient createdIngredient = new Ingredient(newIngredientId, inputName,
          ingredientUnit, ingredientCategory);
      ingredientDAO.save(createdIngredient);

      User dummyUser = new User(1, "Kevin");

      boolean isNewUser = userDAO.userExists(dummyUser);

      if (!isNewUser) {
        userDAO.save(dummyUser);
      }

      InventoryIngredientDAO inventoryIngredientDAO = new InventoryIngredientDAO();

      java.sql.Date inputExpirationDateSQL = new java.sql.Date(inputExpirationDate.getTime());

      InventoryIngredient inventoryIngredient = new InventoryIngredient(dummyUser.getUserId(),
          createdIngredient.getId(), inputAmount, inputExpirationDateSQL, dummyUser.getUserId());
      inventoryIngredientDAO.save(inventoryIngredient, createdIngredient, dummyUser);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
