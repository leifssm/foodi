package no.ntnu.idatt1005.foodi.model.DAO;

import no.ntnu.idatt1005.foodi.model.objects.Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.InventoryIngredient;
import no.ntnu.idatt1005.foodi.model.objects.User;
import no.ntnu.idatt1005.foodi.model.objects.dtos.ExpiringIngredient;

import java.sql.SQLException;

public class ItemDAO {

    private final IngredientDAO ingredientDAO = new IngredientDAO();
    private final UserDAO userDAO = new UserDAO();

    public void saveItem(ExpiringIngredient item) {
        try {
            // Save to Ingredient table
            Ingredient.IngredientUnit ingredientUnit = Ingredient.IngredientUnit.valueOf(item.getUnit().name());
            Ingredient.IngredientCategory ingredientCategory = Ingredient.IngredientCategory.valueOf(item.getCategory().name());

            Ingredient createdIngredient = new Ingredient(item.getId(), item.getName(),
                    ingredientUnit, ingredientCategory);
            ingredientDAO.save(createdIngredient);

            // Save to User table
            User dummyUser = new User(1, "Kevin");
            boolean isNewUser = userDAO.userExists(dummyUser);
            if (!isNewUser) {
                userDAO.save(dummyUser);
            }

            // Save to InventoryIngredient table
            java.sql.Date inputExpirationDateSQL = java.sql.Date.valueOf(item.getExpirationDate());
            InventoryIngredient inventoryIngredient = new InventoryIngredient(dummyUser.getUserId(),
                    createdIngredient.getId(), (int) item.getAmount(), inputExpirationDateSQL, dummyUser.getUserId());
            inventoryIngredientDAO.save(inventoryIngredient, createdIngredient, dummyUser);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}