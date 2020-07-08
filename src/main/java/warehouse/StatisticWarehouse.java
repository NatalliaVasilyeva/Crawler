package warehouse;

import entity.Statistics;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class StatisticWarehouse {

    private static  StatisticWarehouse instance;

    public static StatisticWarehouse getInstance() {
        StatisticWarehouse localInstance = instance;
        if (localInstance == null) {
            synchronized (StatisticWarehouse.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new StatisticWarehouse();
                }
            }
        }
        return localInstance;
    }

    private BlockingQueue<Statistics> statistics = new LinkedBlockingQueue<>();

    public BlockingQueue<Statistics> getStatistics() {
        return statistics;
    }

}
