package utils;

import entity.Statistics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class StatisticBuilder {


    public List<String[]> buildPageStatistic(BlockingQueue<Statistics> statistics) {
        List<String[]> list = new ArrayList<>();

        for (Statistics statistic: statistics ) {
            StringBuilder sb = new StringBuilder();
            sb.append(statistic.getLink()).append(" ");

            statistic
                    .getStatistics()
                    .forEachValue(Long.MAX_VALUE, value -> sb.append(value).append(" "));

            sb.append(countTotalKeywordFrequency(statistic));

            list.add(sb.toString().split(" "));

        }
        return  list;

    }

    public Long countTotalKeywordFrequency (Statistics statistics) {
        return statistics.getStatistics().reduceValues(Long.MAX_VALUE, Long::sum);
    }

}
