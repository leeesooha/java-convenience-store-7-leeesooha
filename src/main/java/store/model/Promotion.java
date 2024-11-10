package store.model;

import camp.nextstep.edu.missionutils.DateTimes;
import java.time.LocalDate;

public class Promotion {
    private final String name;
    private final int buy;
    private final int get;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public Promotion(String name) {
        this.name = name;
        this.buy = 0;
        this.get = 0;
        this.startDate = null;
        this.endDate = null;
    }

    public Promotion(String name, int buy, int get, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public int getBuyPlusGet() {
        return this.get + this.buy;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public boolean isPromotionActive() {
        if (startDate == null || endDate == null) {
            return false;
        }

        LocalDate currentDate = DateTimes.now().toLocalDate();
        if ((currentDate.equals(this.startDate) || currentDate.isAfter(this.startDate))
                && (currentDate.equals(this.endDate) || currentDate.isBefore(this.endDate))) {
            return true;
        }
        return false;
    }
}
