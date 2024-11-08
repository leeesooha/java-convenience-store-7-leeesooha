package store.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import store.dataBase.ProductDB;
import store.dataBase.PromotionDB;
import store.model.Product;
import store.model.Inventory;
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

        Promotion promotion = findOrCreatePromotion(promotionCatalog, promotionName);
        Product product = findOrCreateProduct(inventory, productName, price);
        return new ProductBox(product, promotion, quantity);
    }

    private Product findOrCreateProduct(Inventory inventory, String productName, int price) {
        Product product = inventory.findProductByProductBox(productName);

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

    public List<Product> toProduct(List<String> productData) {
        List<String> cleanedProductData = productData.stream()
                .map(product -> product.replaceAll("^\\[|\\]$", ""))
                .collect(Collectors.toList());
        List<Product> products = new ArrayList<>();

        for (String oneProduct : cleanedProductData) {
            String[] productField = oneProduct.split("-");
            products.add(new Product(productField[0], Integer.parseInt(productField[1])));
        }
        return products;
    }

    public ShoppingCart createShoppingCart(List<String> productData) {
        List<Product> products = this.toProduct(productData);
        return new ShoppingCart(products);
    }
}
