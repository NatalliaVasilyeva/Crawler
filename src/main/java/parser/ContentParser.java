package parser;

import entity.SearchParams;
import entity.Statistics;
import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.ConcurrentHashSet;
import utils.HtmlUtil;
import utils.LinksUtil;
import utils.StatisticCounter;
import warehouse.LinkWarehouse;
import warehouse.StatisticWarehouse;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;


public class ContentParser {

    private StatisticWarehouse statisticWarehouse = StatisticWarehouse.getInstance();
    private LinkWarehouse linkWarehouse = LinkWarehouse.getInstance();
    private SearchParams searchParams;
    private HtmlUtil htmlUtil;
    private LinksUtil linksUtil;
    private StatisticCounter statisticCounter;
    private AtomicLong linkCounter = new AtomicLong(1);
    public static AtomicInteger currentDepth = new AtomicInteger(0);

    public ContentParser(SearchParams searchParams, HtmlUtil htmlUtil, LinksUtil linksUtil, StatisticCounter statisticCounter) {
        this.searchParams = searchParams;
        this.htmlUtil = htmlUtil;
        this.linksUtil = linksUtil;
        this.statisticCounter = statisticCounter;
    }


    public void startCrawling(SearchParams searchParam) {
        searchLinks(searchParam);
        craw(linkWarehouse.getStatistics());
      }

    public void craw(ConcurrentHashSet<String> links) {

        links.forEach(link -> {
            String pageContent = htmlUtil.getContentFromPage(link).toLowerCase();
            ConcurrentHashMap<String, Long> statisticForOnePage = statisticCounter.getAllKeyWordsCountPerOnePage(pageContent, searchParams.getKeyWords());
            statisticWarehouse.getStatistics()
                    .add(new Statistics(link, statisticForOnePage));
        });
    }


    public void searchLinks(SearchParams searchParams) {
        Long maxLimit = searchParams.getLinksCount();
        String url = searchParams.getUrl();
        ConcurrentHashSet<String> links = linkWarehouse.getStatistics();
        links.add(url);
        linkCounter.incrementAndGet();
        Set<String> tempSet = new HashSet<>();
        tempSet.add(url);

        for (int i = 0; i < searchParams.getDepth(); i++) {
            Set<String> set = tempSet;
            tempSet = new HashSet<>();
            for (String link : set) {
                Element elemented = htmlUtil.getElementFromPage(link);
                Elements elements = linksUtil.getLinks(elemented);

                for (Element element : elements) {
                    if (maxLimit > linkCounter.get()) {
                        tempSet.add(linksUtil.removeLinkChar(linksUtil.getHyperLink(element)));
                        linkCounter.incrementAndGet();
                    }
                }
            }

            currentDepth.incrementAndGet();
            for (String link : tempSet) {
                if (UrlValidator.getInstance().isValid(link)) {
                    links.putIfAbsent(link);
                }
            }
        }
    }
}


