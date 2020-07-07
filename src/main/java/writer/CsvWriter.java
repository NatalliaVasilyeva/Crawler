package writer;

import com.opencsv.CSVWriter;
import entity.Statistics;
import utils.StatisticBuilder;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.stream.Stream;

public class CsvWriter {

    private StatisticBuilder statisticBuilder;

    public CsvWriter() {
        this.statisticBuilder = new StatisticBuilder();
    }


    public void writeStatistic(BlockingQueue<Statistics> statistics, Set<String> keyWords, String filename) throws IOException {
        FileWriter fileWriter = new FileWriter(filename);

        CSVWriter csvWriter = new CSVWriter(fileWriter, ',', '\"', '\\', "\n");
        String[] header = Stream.concat(Stream.of("Link"), keyWords.stream()).toArray(String[]::new);
        csvWriter.writeNext(header);

        List<String[]> data = statisticBuilder.buildPageStatistic(statistics);
        data
                .stream().forEach(d->csvWriter.writeNext(d, false));

        csvWriter.flush();

    }


}
