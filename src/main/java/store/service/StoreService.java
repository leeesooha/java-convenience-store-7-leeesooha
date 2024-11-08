package store.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import store.dataBase.ProductDB;
import store.dataBase.PromotionDB;
import store.model.Product;
import store.model.ShoppingItem;
import store.model.StockInventory;
import store.model.ProductBox;
import store.model.Promotion;
import store.model.PromotionCatalog;
import store.model.ShoppingCart;

public class StoreService {
    PromotionDB promotionDB;
    ProductDB productDB;

    public StoreService() {
        this.promotionDB = new PromotionDB();
        this.productDB = new ProductDB();
    }

    public StockInventory createInventory(PromotionCatalog promotionCatalog) {
        List<String> products = productDB.findProduct();
        StockInventory stockInventory = new StockInventory();

        products.stream()
                .map(productData -> toProductBox(productData, promotionCatalog, stockInventory))
                .forEach(stockInventory::addProductBox);

        return stockInventory;
    }

    private ProductBox toProductBox(String productData, PromotionCatalog promotionCatalog, StockInventory stockInventory) {
        List<String> productInfo = Arrays.asList(productData.split(","));
        String productName = productInfo.get(0);
        int price = Integer.parseInt(productInfo.get(1));
        int quantity = Integer.parseInt(productInfo.get(2));
        String promotionName = productInfo.get(3);

        Promotion promotion = findOrCreatePromotion(promotionCatalog, promotionName);
        Product product = findOrCreateProduct(stockInventory, productName, price);
        return new ProductBox(product, promotion, quantity);
    }

    private Product findOrCreateProduct(StockInventory stockInventory, String productName, int price) {
        Product product = stockInventory.findProductByProductName(productName);

        if (product == null) {
            product = new Product(productName, price);
        }
        return product;
    }

    Promotion findOrCreatePromotion(PromotionCatalog promotionCatalog, String promotionName) {
        Promotion promotion = promotionCatalog.findPromotion(promotionName);

        if (promotion == null) {
            promotion = new Promotion("");
        }
        return promotion;
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

    public List<ShoppingItem> toShoppingItem(List<String> shoppingItemData) {
        List<String> cleanedShoppingItemData = shoppingItemData.stream()
                .map(product -> product.replaceAll("^\\[|\\]$", ""))
                .collect(Collectors.toList());
        List<ShoppingItem> ShoppingItem = new ArrayList<>();

        for (String oneShoppingItem : cleanedShoppingItemData) {
            String[] shoppingItemField = oneShoppingItem.split("-");
            ShoppingItem.add(new ShoppingItem(shoppingItemField[0], Integer.parseInt(shoppingItemField[1])));
        }
        return ShoppingItem;
    }

    public ShoppingCart createShoppingCart(List<String> shoppingItemData) {
        List<ShoppingItem> shoppingItems = this.toShoppingItem(shoppingItemData);
        return new ShoppingCart(shoppingItems);
    }
}
