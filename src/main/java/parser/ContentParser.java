package parser;

import entity.SearchParams;
import entity.Statistics;
import utils.HtmlUtil;
import utils.LinksUtil;
import utils.StatisticCounter;
import warehouse.LinkWarehouse;
import warehouse.StatisticWarehouse;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;


public class ContentParser {

    private StatisticWarehouse statisticWarehouse = StatisticWarehouse.getInstance();
    private LinkWarehouse linkWarehouse = LinkWarehouse.getInstance();
    private SearchParams searchParams;
    private HtmlUtil htmlUtil;
    private LinksUtil linksUtil;
    private StatisticCounter statisticCounter;
    private AtomicLong linkLimit = new AtomicLong(1);
    private AtomicInteger currentDepth = new AtomicInteger(1);

    public ContentParser(SearchParams searchParams, HtmlUtil htmlUtil, LinksUtil linksUtil, StatisticCounter statisticCounter) {
        this.searchParams=searchParams;
        this.htmlUtil = htmlUtil;
        this.linksUtil = linksUtil;
        this.statisticCounter = statisticCounter;
    }


    public void startCrawling(SearchParams searchParam) {
        String url = searchParam.getUrl();
        System.out.println(url);
        String pageContent = htmlUtil.getContentFromPage(url).toLowerCase();
        ConcurrentHashMap<String, Long> statisticForOnePage = statisticCounter.getAllKeyWordsCountPerOnePage(pageContent, searchParams.getKeyWords());
        statisticWarehouse.getStatistics().add(new Statistics(url, statisticForOnePage));
        linkLimit.incrementAndGet();
        if(!((currentDepth.incrementAndGet()>searchParams.getDepth()) || linkLimit.get()>searchParam.getLinksCount())) {
            craw(pageContent);
        }
    }


    private SearchParams createSearchParams(String link)  {
        return SearchParams.builder().url(link).depth(currentDepth.get()+1).linksCount(searchParams.getLinksCount()).keyWords(searchParams.getKeyWords()).build();
    }


    public void craw(String pageContent){
        Set<String> links = linksUtil.getLinksFromPage(pageContent);
        List<SearchParams> searchParamsList = links
                .stream()
                .parallel()
                .filter(link -> linkWarehouse.getStatistics().putIfAbsent(link))
                .map(this::createSearchParams)
                .collect(Collectors.toList());

        searchParamsList
                .stream()
                .parallel()
                .limit(searchParams.getLinksCount() - linkLimit.get())
                .forEach(this::startCrawling);
    }

}
