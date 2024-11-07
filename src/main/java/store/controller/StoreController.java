package store.controller;

import java.io.IOException;
import java.util.List;
import store.model.Inventory;
import store.model.Product;
import store.repository.ProductDB;
import store.service.StoreService;
import store.view.InputView;

public class StoreController {
    InputView inputView;
    StoreService storeService;
    ProductDB productDB;

    public StoreController(InputView inputView, StoreService storeService, ProductDB productDB) {
        this.inputView = inputView;
        this.storeService = storeService;
        this.productDB = productDB;
    }

    public void run() throws IOException {
//        while (true) {

        List<String> products = productDB.findProduct();
        Inventory inventory = storeService.addItemToInventory(products);
//        }
    }
}
