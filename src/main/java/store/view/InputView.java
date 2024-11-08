package store.view;

import java.util.Arrays;
import java.util.List;
import store.model.Product;
import camp.nextstep.edu.missionutils.Console;

public class InputView {

    public List<String> inputProduct() {
        List<String> input = Arrays.asList(Console.readLine().split(","));

        return input;
    }
}
