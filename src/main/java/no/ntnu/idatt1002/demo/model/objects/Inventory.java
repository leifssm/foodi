package no.ntnu.idatt1002.demo.model.objects;

import java.sql.Date;

public class Inventory {

    private int inventoryId;

    private int ingredientId;

    private int amount;

    private Date date;

    private int userId;


    public Inventory(int inventoryId, int ingredientId, int amount, Date date, int userId) {
        this.inventoryId = inventoryId;
        this.ingredientId = ingredientId;
        this.amount = amount;
        this.date = date;
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

    public java.util.Date getDate() {
        return date;
    }

    public int getUserId() {
        return userId;
    }

}
