package store.model;

public class ShoppingItem {
    private final String name;
    private int quantity;
    private int promotionQuantity;

    public ShoppingItem(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
        this.promotionQuantity = 0;
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

    public void increaseQuantityBy(int count) {
        this.quantity += count;
    }

    public void setPromotionQuantity(int promotionQuantity) {
        this.promotionQuantity = promotionQuantity;
    }

    public int getPromotionQuantity() {
        return promotionQuantity;
    }
}
