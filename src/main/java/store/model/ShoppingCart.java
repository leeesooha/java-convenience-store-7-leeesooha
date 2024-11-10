package store.model;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    List<ShoppingItem> ShoppingItems;

    public ShoppingCart() {
        this.ShoppingItems = new ArrayList<>();
    }

    public ShoppingCart(List<ShoppingItem> ShoppingItem) {
        this.ShoppingItems = ShoppingItem;
    }

    public List<ShoppingItem> getShoppingItems() {
        return ShoppingItems;
    }

    public void addShoppingItems(List<ShoppingItem> shoppingItems) {
        this.ShoppingItems.addAll(shoppingItems);
    }

    public void clearCart() {
        this.ShoppingItems = new ArrayList<>();
    }
}
