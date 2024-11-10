package store.view;

import java.util.Arrays;
import java.util.List;
import camp.nextstep.edu.missionutils.Console;
import store.constant.Error;

public class InputView {
    private final String YES = "Y";
    private final String NO = "N";
    private final String DELIMITER = ",";
    private final OutputView outputView = new OutputView();
    private final String NEW_LINE = "\n";

    public List<String> inputProduct() {
        List<String> input = Arrays.asList(Console.readLine().split(DELIMITER));

        if (input.size() == 1 && input.get(0).isBlank()) {
            throw new IllegalArgumentException(Error.EMPTY_INPUT.getErrorMessage());
        }

        return input;
    }

    public boolean inputYesOrNo() {
        while (true) {
            String input = Console.readLine();
            if (input.equals(YES)) {
                return true;
            }
            if (input.equals(NO)) {
                return false;
            }
            outputView.display(Error.PURCHASE_FORMAT_MESSAGE.getErrorMessage() + NEW_LINE);
        }
    }
}
