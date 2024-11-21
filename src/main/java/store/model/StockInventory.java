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

    public ProductBox findNormalProductBoxByProductName(String productName) {
        for (ProductBox productBox : productBoxes) {
            if (productBox.getPromotion().getName().isEmpty() &&
                    productBox.getProduct().getName().equals(productName)) {
                return productBox;
            }
        }
        return null;
    }

    //재고가 있는지 확인하는 메소드(프로모션 + 일반재고)
    public boolean isStockAvailable(String productName, int count) {
        ProductBox normalProductBox = findNormalProductBoxByProductName(productName);
        ProductBox promotionProductBox = findPromotionProductBoxByProductName(productName);
        int normalQuantity = 0;
        if (normalProductBox != null) {
            normalQuantity = normalProductBox.getQuantity();
        }
        int promotionQuantity = 0;
        if (promotionProductBox != null) {
            promotionQuantity = promotionProductBox.getQuantity();
        }
        return (normalQuantity + promotionQuantity >= count);
    }

    public boolean isExistProductName(String productName) {
        for (ProductBox productBox : productBoxes) {
            if (productBox.getProduct().getName().equals(productName)) {
                return true;
            }
        }
        return false;
    }

    public int findPriceByProductName(String productName) {
        for (ProductBox productBox : productBoxes) {
            if (productBox.getProduct().getName().equals(productName)) {
                return productBox.getProduct().getPrice();
            }
        }
        return 0;
    }
}
