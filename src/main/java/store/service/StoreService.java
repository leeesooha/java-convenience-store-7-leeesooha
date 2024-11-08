package store.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import store.dataBase.ProductDB;
import store.dataBase.PromotionDB;
import store.enums.Error;
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

//    public void reduceStockByPurchase(StockInventory stockInventory, ShoppingCart shoppingCart) {
//        for (ShoppingItem shoppingItem : shoppingCart.getShoppingItems()) {
//            ProductBox promotionProductBox = stockInventory.findPromotionProductBoxByProductName(shoppingItem.getName());
//            //일반재고 일시
//            if (promotionProductBox == null) { //프로모션 아니면
//                //일반재고 차감
//                 ProductBox normalProductBox = stockInventory.findNormalProductBoxByProductName(shoppingItem.getName());
//                 normalProductBox.decreaseQuantityBy(shoppingItem.getQuantity());
//                 continue;
//            }
//            //프로모션상품 일시
//            //프로모션 재고 충분하면
//            if (shoppingItem.getQuantity() <= promotionProductBox.getQuantity()) {
//                promotionProductBox.decreaseQuantityBy(shoppingItem.getQuantity());
//                continue;
//            }
//            //프로모션 재고가 부족하면
//            //그래도 구매하시겠습니까? 띄우고
//            o
//            if ("구매한다면") {
//                //남은 수량 만큼 일반재고 차감
//                continue;
//            }
//            //구매안한다면
//            //장바구니에 구매 수량 변경해줌. 구매 안하는 수량만큼 빼줌.
//        }
//    }
}
