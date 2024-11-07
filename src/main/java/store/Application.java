package store;

import java.io.IOException;
import store.controller.StoreController;
import store.repository.ProductDB;
import store.service.StoreService;
import store.view.InputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        StoreService storeService = new StoreService();
        ProductDB productDB = new ProductDB();
        StoreController store = new StoreController(inputView, storeService, productDB);
        try {
            store.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
