package store.dataBase;

import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PromotionDB {

    public List<String> findPromotion() {
        while (true) {
            try {
                List<String> input = Files.readAllLines(Paths.get("./src/main/resources/promotions.md"));
                input.remove(0);
                return input;
            } catch (IOException e) {
                System.out.println("파일 읽기에 실패하였습니다.");
                e.printStackTrace();
            }
        }
    }
}
