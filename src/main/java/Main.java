import entity.SearchParams;
import parser.ContentParser;
import utils.HtmlUtil;
import utils.LinksUtil;
import utils.StatisticBuilder;
import utils.StatisticCounter;
import warehouse.StatisticWarehouse;
import writer.CsvWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {

//        String seedLink = "https://en.wikipedia.org/wiki/Elon_Musk";
        String seedLink = "https://tut.by";
        Integer depth = 2;
        Long linkCount = 5l;
        Set<String> keyWords = new HashSet<>();
        keyWords.addAll(Arrays.asList("Tesla", "Musk", "Gigafactory", "Elon Mask"));
        System.out.println(keyWords);

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


        System.out.println(searchParams);


        ContentParser parser = new ContentParser(searchParams, new HtmlUtil(), new LinksUtil(), new StatisticCounter());
        parser.startCrawling(searchParams);


            CsvWriter csvWriter = new CsvWriter();
            csvWriter.writeStatistic(StatisticWarehouse.getInstance().getStatistics(), keyWords, "result.csv");

        }


}
