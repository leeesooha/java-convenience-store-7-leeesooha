package store.model;

import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<ProductBox> productBoxes;

    public Inventory() {
        productBoxes = new ArrayList<>();
    }

    public void addProductBox(ProductBox productBox) {
        this.productBoxes.add(productBox);
    }

    public Product findProductByProductBox(String productName) {
        for (ProductBox productBox : productBoxes) {
            if (productBox.isMatchProduct(productName)) {
                return productBox.getProduct();
            }
        }
        return null;
    }
}
