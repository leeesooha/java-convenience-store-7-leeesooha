package store.controller;

import java.util.List;
import store.enums.Message;
import store.model.ProductBox;
import store.model.ShoppingItem;
import store.model.StockInventory;
import store.model.PromotionCatalog;
import store.model.ShoppingCart;
import store.service.StoreService;
import store.view.InputView;
import store.view.OutputView;

public class StoreController {
    InputView inputView;
    OutputView outputView;
    StoreService storeService;

    public StoreController(InputView inputView, OutputView outputView, StoreService storeService) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.storeService = storeService;
    }

    public void run() {
        PromotionCatalog promotionCatalog = storeService.createPromotion();
        StockInventory stockInventory = storeService.createInventory(promotionCatalog);
        ShoppingCart shoppingCart = new ShoppingCart();
        while (true) {
            outputView.displayInventory(stockInventory);
            purchaseProduct(stockInventory, shoppingCart);
            receiptHandler(stockInventory, shoppingCart);
            if (!askToContinueShopping()) {
                return;
            }
        }
    }
    private void receiptHandler(StockInventory stockInventory, ShoppingCart shoppingCart) {
        outputView.display(Message.MEMBERSHIP_MESSAGE);
        if (inputView.inputYesOrNo()) {
            outputView.displayReceipt(stockInventory, shoppingCart, Discount.MEMBERSHIP_DISCOUNT_PERCENT);
        }
    }

    private void purchaseProduct(StockInventory stockInventory, ShoppingCart shoppingCart) {
        while (true) {
            shoppingCart.addShoppingItems(addProductToCart());
//            shoppingCart = addProductToCart();
            try {
                storeService.validateUserInput(stockInventory, shoppingCart);
                reduceStockByPurchase(stockInventory, shoppingCart);
            } catch (IllegalArgumentException e) {
                outputView.display(e.getMessage());
                continue;
            }
            return;
        }
    }

    private List<ShoppingItem> addProductToCart() {
        outputView.displayPurchaseMessage();
        List<String> productData = inputView.inputProduct();
        return storeService.createShoppingCart(productData);
    }

    public void reduceStockByPurchase(StockInventory stockInventory, ShoppingCart shoppingCart) {
        for (ShoppingItem shoppingItem : shoppingCart.getShoppingItems()) {
            ProductBox promotionProductBox = stockInventory.findPromotionProductBoxByProductName(
                    shoppingItem.getName());
            //일반재고 일시
            if (promotionProductBox == null) {
                reduceNormalStock(stockInventory, shoppingItem);
                continue;
            }
            //프로모션상품 일시
            //- 2+1인데 6개 가져옴 -> 2개증정
            //- 2+1인데 7개 가져옴 -> 그냥 구매
            //- 2+1인데 8개 가져옴 -> 2개증정 , 1개 더 증정 가능
            //- 2+1인데 11개 가져옴 -> 3개증정, 1개 더 증정 가능
            handlePromotionStock(shoppingItem, promotionProductBox, stockInventory);
        }
    }

    private void handlePromotionStock(ShoppingItem shoppingItem, ProductBox promotionProductBox,
                                      StockInventory stockInventory) {
        int requireQuantity = promotionProductBox.getPromotion().getBuy();
        int ableBonusQuantity = calculateAbleBonusQuantity(shoppingItem, promotionProductBox, requireQuantity);

        //프로모션 재고 충분하면
        if (isEnoughStockPromotion(shoppingItem, ableBonusQuantity, promotionProductBox)) {
            handleEnoughPromotionStock(shoppingItem, promotionProductBox, stockInventory, ableBonusQuantity);
            return;
        }
        //프로모션을 모두 적용시켜주기엔 프로모션 재고가 부족하다면
        handleNotEnoughPromotionStock(shoppingItem, promotionProductBox, stockInventory);
    }

    private int calculateAbleBonusQuantity(ShoppingItem shoppingItem, ProductBox promotionProductBox,
                                           int requireQuantity) {
        int ableBonusQuantity = 0;

        if (shoppingItem.getQuantity() % promotionProductBox.getPromotion().getBuyPlusGet() == requireQuantity) {
            ableBonusQuantity = promotionProductBox.getPromotion().getGet();
        }
        return ableBonusQuantity;
    }

    private boolean isEnoughStockPromotion(ShoppingItem shoppingItem, int ableBonusQuantity,
                                           ProductBox promotionProductBox) {
        int needStockQuantity = shoppingItem.getQuantity() + ableBonusQuantity;

        return needStockQuantity <= promotionProductBox.getQuantity();
    }

    private void handleEnoughPromotionStock(ShoppingItem shoppingItem, ProductBox promotionProductBox,
                                            StockInventory stockInventory, int ableBonusQuantity) {
        //N+1 더 들고 와야 할떄
        if (ableBonusQuantity != 0) {
            processBonusItems(shoppingItem, promotionProductBox, ableBonusQuantity);
            return;
        }
        //프로모션 상품 바로 구매
        reducePromotionQuantity(shoppingItem, promotionProductBox);
    }

    private void handleNotEnoughPromotionStock(ShoppingItem shoppingItem, ProductBox promotionProductBox,
                                               StockInventory stockInventory) {
        int applyQuantity = ((promotionProductBox.getQuantity() / promotionProductBox.getPromotion().getBuyPlusGet()))
                * promotionProductBox.getPromotion().getBuyPlusGet();
        outputView.displayConfirmPurchase(shoppingItem.getName(), shoppingItem.getQuantity() - applyQuantity);
        boolean isNotApplyProductPurchaseConfirm = inputView.inputYesOrNo();
        if (isNotApplyProductPurchaseConfirm) { //재고 2개 일떄 2+1 인데 구매는 3개//남은 수량 만큼 일반재고 차감
            reduceNormalAndPromotionQuantity(stockInventory, promotionProductBox, shoppingItem);
            return;
        }
        removeNotApplyPromotion(shoppingItem, promotionProductBox); //구매안한다면 장바구니에 구매 수량 변경해줌. 구매 안하는 수량만큼 빼줌.
        promotionProductBox.decreaseQuantityBy(promotionProductBox.getQuantity());
    }

    private void removeNotApplyPromotion(ShoppingItem shoppingItem, ProductBox promotionProductBox) {
        int notApplyPromotionQuantity = shoppingItem.getQuantity() % promotionProductBox.getPromotion()
                .getBuyPlusGet();
        shoppingItem.setPromotionQuantity(
                promotionProductBox.getQuantity() / promotionProductBox.getPromotion().getBuyPlusGet());
        shoppingItem.decreaseQuantityBy(notApplyPromotionQuantity);
    }

    private void reduceNormalAndPromotionQuantity(StockInventory stockInventory, ProductBox promotionProductBox,
                                                  ShoppingItem shoppingItem) {
        ProductBox normalProductBox = stockInventory.findNormalProductBoxByProductName(shoppingItem.getName());
        int remainQuantity = shoppingItem.getQuantity() - promotionProductBox.getQuantity();
        //일반상품에서 더 가져와야 할떄
        promotionProductBox.decreaseQuantityBy(shoppingItem.getQuantity());
        shoppingItem.setPromotionQuantity(
                shoppingItem.getQuantity() / promotionProductBox.getPromotion().getBuyPlusGet());
        normalProductBox.decreaseQuantityBy(remainQuantity);
    }

    private void reducePromotionQuantity(ShoppingItem shoppingItem, ProductBox promotionProductBox) {
        promotionProductBox.decreaseQuantityBy(shoppingItem.getQuantity());
        shoppingItem.setPromotionQuantity(
                shoppingItem.getQuantity() / promotionProductBox.getPromotion().getBuyPlusGet());
    }

    private void processBonusItems(ShoppingItem shoppingItem, ProductBox promotionProductBox, int ableBonusQuantity) {
        int getPlusBuyCount = promotionProductBox.getPromotion().getBuyPlusGet();
        outputView.displayFreeProductConfirm(promotionProductBox.getProduct().getName(), ableBonusQuantity);
        if (inputView.inputYesOrNo()) { //추가 증정 YES
            promotionProductBox.decreaseQuantityBy(shoppingItem.getQuantity() + ableBonusQuantity);
            shoppingItem.setPromotionQuantity(
                    (shoppingItem.getQuantity() + ableBonusQuantity) / getPlusBuyCount);
            return;
        }
        promotionProductBox.decreaseQuantityBy(shoppingItem.getQuantity());//추가 증정 NO //2+1인데 5개만 삼
        shoppingItem.setPromotionQuantity(shoppingItem.getQuantity() / getPlusBuyCount);
    }

    private void reduceNormalStock(StockInventory stockInventory, ShoppingItem shoppingItem) {
        ProductBox normalProductBox = stockInventory.findNormalProductBoxByProductName(shoppingItem.getName());
        normalProductBox.decreaseQuantityBy(shoppingItem.getQuantity());
    }

}
