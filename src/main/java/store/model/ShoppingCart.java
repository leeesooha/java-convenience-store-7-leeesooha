package store.model;

import java.util.List;

public class ShoppingCart {
    List<ShoppingItem> ShoppingItems;

    public ShoppingCart (List<ShoppingItem> ShoppingItem) {
        this.ShoppingItems = ShoppingItem;
    }

    public List<ShoppingItem> getShoppingItems() {
        return ShoppingItems;
    }
}
