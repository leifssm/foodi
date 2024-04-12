package no.ntnu.idatt1005.foodi.controller;

import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.DAO.InventoryDAO;
import no.ntnu.idatt1005.foodi.model.DAO.UserDAO;
import no.ntnu.idatt1005.foodi.model.objects.*;

import java.sql.SQLException;
import java.util.Date;

/**
 * This class is responsible for handling the usage of
 * items in the inventory
 * back connecting Ingredient, User and Inventory Classes
 *
 * @version 0.2.0
 * @author Kevin Dennis Mazali
 */

public class ItemController {
  private IngredientDAO ingredientDAO;
  private UserDAO userDAO; // antar at du har en UserDAO klasse


  public ItemController() {
    ingredientDAO = new IngredientDAO();
    userDAO = new UserDAO(); // initialiser UserDAO
  }

  public void save(String inputName, IngredientCategory inputCategory, IngredientUnit inputUnit, int inputAmount, Date inputExpirationDate) {
    int IngredientIdFillerValue = 0;
    try {
      IngredientIdFillerValue += 1;

      Ingredient.IngredientCategory ingredientCategory = Ingredient.IngredientCategory.valueOf(inputCategory.name());
      Ingredient.IngredientUnit ingredientUnit = Ingredient.IngredientUnit.valueOf(inputUnit.name());

      Ingredient createdIngredient = new Ingredient(IngredientIdFillerValue, inputName, ingredientUnit, ingredientCategory);
      ingredientDAO.save(createdIngredient);

      User dummyUser = new User(1,"Kevin");
      userDAO.save(dummyUser);

      InventoryDAO inventoryDAO = new InventoryDAO();

      //from util.date to sql.date
      java.sql.Date inputExpirationDateSQL = new java.sql.Date(inputExpirationDate.getTime());

      Inventory inventory = new Inventory(dummyUser.getUserId(), createdIngredient.getId(),inputAmount, inputExpirationDateSQL,dummyUser.getUserId());
      inventoryDAO.save(inventory,createdIngredient,dummyUser);

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
