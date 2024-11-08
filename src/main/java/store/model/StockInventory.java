package store.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StockInventory {
    private List<ProductBox> productBoxes;

    public StockInventory() {
        productBoxes = new ArrayList<>();
    }

    public void addProductBox(ProductBox productBox) {
        this.productBoxes.add(productBox);
    }

    public Product findProductByProductName(String productName) {
        for (ProductBox productBox : productBoxes) {
            if (productBox.isMatchProduct(productName)) {
                return productBox.getProduct();
            }
        }
        return null;
    }

    public List<ProductBox> getProductBoxes() {
        return Collections.unmodifiableList(productBoxes);
    }

    public ProductBox findPromotionProductBoxByProductName(String productName) {
        for (ProductBox productBox : productBoxes) {
            if (!productBox.getPromotion().getName().isEmpty() &&
                    productBox.getProduct().getName().equals(productName)) {
                return productBox;
            }
        }
        return null;
    }
}
