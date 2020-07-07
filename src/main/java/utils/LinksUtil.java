package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.stream.Collectors;

public class LinksUtil {

    public ConcurrentHashSet<String> getLinksFromPage(String htmlBodyAsString) {
        return Jsoup
                .parse(htmlBodyAsString)
                .body()
                .select("a[href]")
                .stream()
                .map(hyperLink -> getHyperLink(hyperLink))
                .collect(Collectors.toCollection(ConcurrentHashSet::new));
    }

    private String getHyperLink(Element page) {
        return page.attr("abs:href");

    }

}
