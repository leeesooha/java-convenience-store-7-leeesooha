package store.model;

public class ProductBox {
    private Product product;
    private final int quantity;
    private Promotion promotion;

    public ProductBox(Product product, Promotion promotion, int quantity) {
        this.product = product;
        this.quantity = quantity;
        this.promotion = promotion;
    }

    private void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    public void applyPromotion(Promotion promotion) {
        this.setPromotion(promotion);
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
}
