package store.service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import store.dataBase.ProductDB;
import store.dataBase.PromotionDB;
import store.model.Product;
import store.model.Inventory;
import store.model.Promotion;
import store.model.PromotionCatalog;

public class StoreService {
    PromotionDB promotionDB;
    ProductDB productDB;

    public StoreService() {
        this.promotionDB = new PromotionDB();
        this.productDB = new ProductDB();
    }

    public Inventory createInventory() {
        List<String> products = productDB.findProduct();
        Inventory inventory = new Inventory();

        products.stream()
                .map(this::toProduct)
                .forEach(inventory::addProduct);

        return inventory;
    }

    private Product toProduct(String productData) {
        List<String> productInfo = Arrays.asList(productData.split(","));
        String name = productInfo.get(0);
        int price = Integer.parseInt(productInfo.get(1));
        int quantity = Integer.parseInt(productInfo.get(2));
        String promotion = productInfo.get(3);
        return new Product(name, price, quantity, promotion);
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
