package store.dataBase;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ProductDB {

    public List<String> findProduct() {
        while (true) {
            try {
                List<String> input = Files.readAllLines(Paths.get("./src/main/resources/products.md"));
                input.remove(0);
                return input;
            } catch (IOException e) {
                System.out.println("파일 읽기에 실패하였습니다.");
                e.printStackTrace();
            }
        }
    }
}
