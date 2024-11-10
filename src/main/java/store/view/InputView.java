package store.view;

import java.util.Arrays;
import java.util.List;
import camp.nextstep.edu.missionutils.Console;

public class InputView {
    private final String YES = "Y";
    private final String DELIMITER = ",";

    public List<String> inputProduct() {
        List<String> input = Arrays.asList(Console.readLine().split(DELIMITER));

        return input;
    }

    public boolean inputYesOrNo() {
        String input = Console.readLine();
        if (input.equals(YES)) {
            return true;
        }
        return false;
    }
}
