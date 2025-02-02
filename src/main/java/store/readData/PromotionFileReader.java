package store.readData;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import store.constant.Error;
import store.constant.File;

public class PromotionFileReader {

    public List<String> readPromotion() {
        while (true) {
            try {
                List<String> input = Files.readAllLines(Paths.get(File.PROMOTION_FILE_PATH));
                input.remove(File.COLUMN_HEADERS);
                return input;
            } catch (IOException e) {
                System.out.println(Error.FILE_ERROR);
                e.printStackTrace();
            }
        }
    }
}
