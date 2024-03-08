package no.ntnu.idatt1002.demo.model.objects;

import java.sql.Date;

public class Inventory {

    private int inventoryId;

    private int ingredientId;

    private int amount;

    private Date experationDate;

    private int userId;


    public Inventory(int inventoryId, int ingredientId, int amount, Date experationDate, int userId) {
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

    public int getAmount() {
        return amount;
    }

    public java.util.Date getExperationDate() {
        return experationDate;
    }

    public int getUserId() {
        return userId;
    }

}
