package store.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import store.dataBase.ProductDB;
import store.dataBase.PromotionDB;
import store.model.Product;
import store.model.Inventory;
import store.model.ProductBox;
import store.model.Promotion;
import store.model.PromotionCatalog;

public class StoreService {
    PromotionDB promotionDB;
    ProductDB productDB;

    public StoreService() {
        this.promotionDB = new PromotionDB();
        this.productDB = new ProductDB();
    }

    public Inventory createInventory(PromotionCatalog promotionCatalog) {
        List<String> products = productDB.findProduct();
        Inventory inventory = new Inventory();

        products.stream()
                .map(productData -> toProductBox(productData, promotionCatalog, inventory))
                .forEach(inventory::addProductBox);

        return inventory;
    }

    private ProductBox toProductBox(String productData, PromotionCatalog promotionCatalog, Inventory inventory) {
        List<String> productInfo = Arrays.asList(productData.split(","));
        String productName = productInfo.get(0);
        int price = Integer.parseInt(productInfo.get(1));
        int quantity = Integer.parseInt(productInfo.get(2));
        String promotionName = productInfo.get(3);

        Promotion promotion = promotionCatalog.findPromotion(promotionName);
        //오늘날짜가 프로모션기간이내가 아니라면 프로모션 빈문자열 할당.
        if (promotion == null) {
            promotion = new Promotion("");
        }

        Product product = inventory.findProductByProductBox(productName);
        if (product == null) {
            product = new Product(productName, price);
        }
        return new ProductBox(product, promotion, quantity);
    }

    public PromotionCatalog createPromotion() {
        PromotionCatalog promotionCatalog = new PromotionCatalog();
        List<String> promotions = promotionDB.findPromotion();

        promotions.stream().
                map(this::toPromotion).
                forEach(promotionCatalog::addPromotion);

        return promotionCatalog;
    }

    private Promotion toPromotion(String promotionData) {
        List<String> promotionField = Arrays.asList(promotionData.split(","));
        String name = promotionField.get(0);

        int buy = Integer.parseInt(promotionField.get(1));
        int get = Integer.parseInt(promotionField.get(2));
        LocalDate startDate = LocalDate.parse(promotionField.get(3));
        LocalDate endDate = LocalDate.parse(promotionField.get(4));
        return new Promotion(name, buy, get, startDate, endDate);
    }
}
