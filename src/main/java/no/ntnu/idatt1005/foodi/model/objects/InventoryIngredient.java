package no.ntnu.idatt1005.foodi.model.objects;

import java.sql.Date;

public class InventoryIngredient {

    private final int inventoryId;

    private final int ingredientId;

    private final double amount;

    private final Date experationDate;

    private final int userId;


    public InventoryIngredient(int inventoryId, int ingredientId, int amount, Date experationDate, int userId) {
        this.inventoryId = inventoryId;
        this.ingredientId = ingredientId;
        this.amount = amount;
        this.experationDate = experationDate;
        this.userId = userId;


    }

    public int getInventoryId() {
        return inventoryId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public double getAmount() {return amount;}

    public java.util.Date getExperationDate() {
        return experationDate;
    }

    public int getUserId() {
        return userId;
    }

}
