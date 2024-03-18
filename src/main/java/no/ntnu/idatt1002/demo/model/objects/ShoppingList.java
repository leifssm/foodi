package no.ntnu.idatt1002.demo.model.objects;

public class ShoppingList {

    private int shoppingListId;
    private int ingredientId;
    private int amount;
    private int userId;

    // evt. is bought, for å kunne autoslette, når den er true, sånn når noen velger remove item, så endrer den til true, og sånn at den fjernes fra listen

    public ShoppingList(int shoppingListId, int ingredientId, int amount, int userId) {
        this.shoppingListId = shoppingListId;
        this.ingredientId = ingredientId;
        this.amount = amount;
        this.userId = userId;
    }

    public int getShoppingListId() {
        return shoppingListId;
    }

    public int getIngredientId() {
        return ingredientId;
    }

    public int getAmount() {
        return amount;
    }

    public int getUserId() {
        return userId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
