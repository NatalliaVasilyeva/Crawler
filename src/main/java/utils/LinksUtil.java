package utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.select.Elements;

import java.util.stream.Collectors;

public class LinksUtil {

    public String getHyperLink(Element page) {
        return page.attr("abs:href");
    }

    public Elements getLinks(Element element) {
        return element
                .select("a[href]")
                .stream()
                .collect(Collectors.toCollection(Elements::new));

    }

    public String removeLinkChar(String url) {
       return  url.split("#", 2)[0];
    }
}
