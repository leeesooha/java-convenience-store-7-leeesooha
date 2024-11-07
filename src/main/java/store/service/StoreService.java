package store.service;

import java.util.Arrays;
import java.util.List;
import store.model.Product;
import store.model.Inventory;

public class StoreService {


    public Inventory addItemToInventory(List<String> input) {
        Inventory inventory = new Inventory();

        for (String oneProduct : input) {
            List<String> productInfo = Arrays.asList(oneProduct.split(","));
            String name = productInfo.get(0);
            int price = Integer.parseInt(productInfo.get(1));
            int quantity = Integer.parseInt(productInfo.get(2));
            String promotion = productInfo.get(3);
            inventory.addProduct(new Product(name, price, quantity, promotion));
        }
        return inventory;
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
