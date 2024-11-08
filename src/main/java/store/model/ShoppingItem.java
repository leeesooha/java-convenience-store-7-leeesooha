package store.model;

public class ShoppingItem {
    private final String name;
    private int quantity;

    public ShoppingItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void decreaseQuantityBy(int notApplyPromotionQuantity) {
        this.quantity -= notApplyPromotionQuantity;
    }
}
