package store.view;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InputView {

    public List<String> inputProductToPurchase() throws IOException {
        List<String> input = new ArrayList<>();

        while (true) {
            try {
               input = Files.readAllLines(Paths.get("./src/main/resources/products.md"));
               input.remove(0);
               return input;
            } catch (IOException e) {
                throw new IOException("파일 읽기에 실패하였습니다." ,e);
            }
        }
    }
}
