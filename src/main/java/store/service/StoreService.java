package store.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import store.readData.ProductFileReader;
import store.readData.PromotionFileReader;
import store.constant.Error;
import store.model.Product;
import store.model.ShoppingItem;
import store.model.StockInventory;
import store.model.ProductBox;
import store.model.Promotion;
import store.model.PromotionCatalog;
import store.model.ShoppingCart;

public class StoreService {
    private final PromotionFileReader promotionFileReader;
    private final ProductFileReader productFileReader;

    public StoreService() {
        this.promotionFileReader = new PromotionFileReader();
        this.productFileReader = new ProductFileReader();
    }

    public StockInventory createInventory(PromotionCatalog promotionCatalog) {
        List<String> products = productFileReader.readProduct();
        StockInventory stockInventory = new StockInventory();

        for (String productData : products) {
            ProductBox productBox = toProductBox(productData, promotionCatalog, stockInventory);
            stockInventory.addProductBox(productBox);
        }
        adjustStockInventory(stockInventory);
        return stockInventory;
    }

    private void adjustStockInventory(StockInventory stockInventory) {
        List<ProductBox> additionalProductBoxes = new ArrayList<>(); // 추가할 항목을 저장할 임시 리스트
        for (ProductBox productBox : stockInventory.getProductBoxes()) {
            ProductBox normalProductBox = stockInventory.findNormalProductBoxByProductName(
                    productBox.getProduct().getName());
            if (!productBox.getPromotion().getName().isEmpty() && normalProductBox == null) {
                Product addProduct = new Product(productBox.getProduct().getName(), productBox.getProduct().getPrice());
                additionalProductBoxes.add(new ProductBox(addProduct, new Promotion(""), 0)); // 임시 리스트에 추가
            }
        }
        addProductBoxes(stockInventory, additionalProductBoxes);
    }

    private void addProductBoxes(StockInventory stockInventory, List<ProductBox> additionalProductBoxes) {
        // 순회가 끝난 후 stockInventory에 새로운 ProductBox들을 추가
        for (ProductBox additionalProductBox : additionalProductBoxes) {
            stockInventory.addProductBox(additionalProductBox);
        }
    }

    private ProductBox toProductBox(String productData, PromotionCatalog promotionCatalog,
                                    StockInventory stockInventory) {
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
        List<String> promotions = promotionFileReader.readPromotion();

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

    public List<ShoppingItem> createShoppingCart(List<String> shoppingItemData) {
        List<ShoppingItem> shoppingItems = this.toShoppingItem(shoppingItemData);
        return shoppingItems;
    }

    public void checkAllStockAvailable(StockInventory stockInventory, ShoppingCart shoppingCart) {
        for (ShoppingItem shoppingItem : shoppingCart.getShoppingItems()) {
            if (!stockInventory.isStockAvailable(shoppingItem.getName(), shoppingItem.getQuantity())) {
                throw new IllegalArgumentException(Error.INSUFFICIENT_STOCK_MESSAGE.getErrorMessage());
            }
        }
    }

    public void checkAllExistProductName(StockInventory stockInventory, ShoppingCart shoppingCart) {
        for (ShoppingItem shoppingItem : shoppingCart.getShoppingItems()) {
            if (!stockInventory.isExistProductName(shoppingItem.getName())) {
                throw new IllegalArgumentException(Error.NONE_EXIST_STOCK_MESSAGE.getErrorMessage());
            }
        }
    }

    public void validateUserInput(StockInventory stockInventory, ShoppingCart shoppingCart) {
        this.checkAllExistProductName(stockInventory, shoppingCart);
        this.checkAllStockAvailable(stockInventory, shoppingCart);
    }
}
