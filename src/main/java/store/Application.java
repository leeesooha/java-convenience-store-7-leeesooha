package store;

import java.io.IOException;
import store.controller.StoreController;
import store.service.StoreService;
import store.view.InputView;

public class Application {
    public static void main(String[] args) {
        InputView inputView = new InputView();
        StoreService storeService = new StoreService();
        StoreController store = new StoreController(inputView, storeService);
        try {
            store.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
