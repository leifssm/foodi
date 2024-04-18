package no.ntnu.idatt1005.foodi.model.DAO;

import no.ntnu.idatt1005.foodi.model.objects.dtos.ExpiringIngredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.Ingredient;
import no.ntnu.idatt1005.foodi.model.objects.dtos.User;

import java.sql.Date;

// Vet ikke helt om denne klassen

public class ItemDAO {

    private final IngredientDAO ingredientDAO = new IngredientDAO();
    private final UserDAO userDAO = new UserDAO();

    public void saveItem(ExpiringIngredient item) {
        // Save to Ingredient table
        Ingredient.Unit ingredientUnit = Ingredient.Unit.valueOf(item.getUnit().name());
        Ingredient.Category ingredientCategory = Ingredient.Category.valueOf(item.getCategory().name());

        Ingredient createdIngredient = new Ingredient(item.getId(), item.getName(),
              ingredientUnit, ingredientCategory);
        ingredientDAO.saveIngredientObject(createdIngredient);

        // Save to User table
        User dummyUser = new User(1, "Kevin");
        userDAO.saveUser(dummyUser.name());

        // Save to InventoryIngredient table
        Date inputExpirationDateSQL = Date.valueOf(item.getExpirationDate());
        new QueryBuilder("INSERT INTO inventory (ingredient_id, amount, expiration_date, user_id) VALUES (?, ?, ?, ?)")
              .addInt(createdIngredient.getId())
              .addDouble(item.getAmount())
              .addDate(inputExpirationDateSQL)
              .addInt(dummyUser.userId())
              .executeUpdateSafe();
    }
}