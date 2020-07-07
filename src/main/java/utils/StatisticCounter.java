package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class StatisticCounter {

    private static String regex = "%s";
    private static String example = ":matchesOwn((?i)%s)";

    public ConcurrentHashMap<String, Long> getAllKeyWordsCountPerOnePage(String htmlBodyAsString, Set<String> keyWords) {
        String pageContent = Jsoup.parse(htmlBodyAsString).body().text();
        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();

        keyWords
                .forEach(keyword -> map.putIfAbsent(keyword, getOneWordkeyCount(pageContent, keyword)));

        return map;

    }

    private Long getOneWordkeyCount(String pageContent, String keyWord) {

        return Pattern.compile(String.format(regex, keyWord).toLowerCase()).matcher(pageContent).results().count();
    }

}
