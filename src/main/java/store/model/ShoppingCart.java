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

    public int findBonusQuantityByProductName(String productName) {
        for (ShoppingItem shoppingItem : ShoppingItems) {
            if (shoppingItem.getName().equals(productName)) {
                shoppingItem.getPromotionQuantity();
            }
        }
        return 0;
    }

    public void clearCart() {
        this.ShoppingItems = new ArrayList<>();
    }
}
