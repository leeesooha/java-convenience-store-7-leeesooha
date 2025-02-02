package store.model;

public class ProductBox {
    private Product product;
    private int quantity;
    private Promotion promotion;

    public ProductBox(Product product, Promotion promotion, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    public boolean isMatchProduct(String productName) {
        return product.getName().equals(productName);
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void decreaseQuantityBy(int count) {
        if (this.quantity - count <= 0) {
            this.quantity = 0;
            return;
        }
        this.quantity -= count;
    }

    public boolean isPromotionActive() {
        if (this.promotion.isPromotionActive()) {
            return true;
        }
        return false;
    }
}
