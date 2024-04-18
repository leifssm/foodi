package no.ntnu.idatt1005.foodi.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import no.ntnu.idatt1005.foodi.model.DAO.IngredientDAO;
import no.ntnu.idatt1005.foodi.model.DAO.ItemDAO;
import no.ntnu.idatt1005.foodi.model.objects.dtos.ExpiringIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;

/**
 * This class is responsible for handling the usage of items in the inventory back connecting
 * Ingredient, User and Inventory Classes
 *
 * @author Kevin Dennis Mazali
 * @version 0.2.0
 */

public class ItemController {
  private final ItemDAO itemDAO;
    private final IngredientDAO ingredientDAO;

  public ItemController() {
    this.itemDAO = new ItemDAO();
    this.ingredientDAO = new IngredientDAO();
  }


  // nySaveItem metode som lager en DTO object
  public void saveItem(String inputName, Ingredient.Category inputCategory, Ingredient.Unit inputUnit,
                        int inputAmount, Date inputExpirationDate) {
    final int newIngredientId = ingredientDAO.countIngredientItems() + 1;
    System.out.println("IngredientIdFillerValue: " + newIngredientId);

      no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient.Unit ingredientUnit = no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient.Unit.valueOf(inputUnit.name());
      no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient.Category ingredientCategory = no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient.Category.valueOf(inputCategory.name());
      LocalDate localExpirationDate = inputExpirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

      ExpiringIngredient createdIngredient = new ExpiringIngredient(newIngredientId, inputName,
              ingredientUnit, ingredientCategory, inputAmount, localExpirationDate);

      itemDAO.saveItem(createdIngredient);

  }
}
