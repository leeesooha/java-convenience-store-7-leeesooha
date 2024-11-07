package store.controller;

import java.io.IOException;
import store.model.Inventory;
import store.model.PromotionCatalog;
import store.service.StoreService;
import store.view.InputView;

public class StoreController {
    InputView inputView;
    StoreService storeService;

    public StoreController(InputView inputView, StoreService storeService) {
        this.inputView = inputView;
        this.storeService = storeService;
    }

    public void run() throws IOException {
        PromotionCatalog promotionCatalog = storeService.createPromotion();
        Inventory inventory = storeService.createInventory();

//        while (true) {
//        }
    }
}
