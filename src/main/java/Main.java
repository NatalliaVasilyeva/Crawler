import entity.SearchParams;
import parser.ContentParser;
import utils.HtmlUtil;
import utils.LinksUtil;
import utils.StatisticCounter;
import warehouse.StatisticWarehouse;
import writer.CsvWriter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {

        String seedLink = "https://en.wikipedia.org/wiki/Elon_Musk";
//        String seedLink = "https://tut.by";
        Integer depth = 8;
        Long linkCount = 200l;
        Set<String> keyWords = new HashSet<>();
        keyWords.addAll(Arrays.asList("Tesla", "Musk", "Gigafactory", "Elon Mask"));

//        for (int i = 5; i < args.length; i++) {
//            keyWords.add(args[i]);
//        }

        SearchParams searchParams = SearchParams
                .builder()
                .url(seedLink)
                .depth(depth)
                .linksCount(linkCount)
                .keyWords(keyWords)
                .build();



        ContentParser parser = new ContentParser(searchParams, new HtmlUtil(), new LinksUtil(), new StatisticCounter());
        parser.startCrawling(searchParams);


            CsvWriter csvWriter = new CsvWriter();
            csvWriter.writeStatistic(StatisticWarehouse.getInstance().getStatistics(), keyWords, "result.csv");

        }


}
