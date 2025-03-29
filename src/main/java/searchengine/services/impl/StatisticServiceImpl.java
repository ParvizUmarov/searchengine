package searchengine.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import searchengine.config.SiteInfo;
import searchengine.config.SitesList;
import searchengine.dto.model.LemmaDto;
import searchengine.dto.statistics.DetailedStatisticsItem;
import searchengine.dto.statistics.StatisticsData;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.dto.statistics.TotalStatistics;
import searchengine.services.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticsService {

    private final SitesList sites;
    private final IndexService indexService;
    private final SiteService siteService;
    private final PageService pageService;
    private final LemmaService lemmaService;

    @Override
    public StatisticsResponse getStatistics() {

        TotalStatistics total = new TotalStatistics();
        total.setSites(sites.getSites().size());
        total.setIndexing(true);

        List<DetailedStatisticsItem> detailed = new ArrayList<>();
        List<SiteInfo> sitesList = sites.getSites();
        for(int i = 0; i < sitesList.size(); i++) {
            SiteInfo siteInfo = sitesList.get(i);

            var findSite = siteService.findByName(siteInfo.getName());
            List<LemmaDto> siteLemmas = new ArrayList<>();

            DetailedStatisticsItem item = new DetailedStatisticsItem();

            if(findSite != null){
                siteLemmas = lemmaService.findLemmaBySiteId(findSite.getId());

                int pages = pageService.getCountOfPagesBySiteId(findSite.getId());
                int lemmas = siteLemmas.size();

                total.setPages(total.getPages() + pages);
                total.setLemmas(total.getLemmas() + lemmas);

                item.setPages(pages);
                item.setLemmas(lemmas);
                item.setStatus(findSite.getStatus().name());
                item.setError(findSite.getLastError());
                item.setStatusTime(findSite.getDate().getTime());
            }

            item.setName(siteInfo.getName());
            item.setUrl(siteInfo.getUrl());

            detailed.add(item);
        }

        StatisticsResponse response = new StatisticsResponse();
        StatisticsData data = new StatisticsData();
        data.setTotal(total);
        data.setDetailed(detailed);
        response.setStatistics(data);
        response.setResult(true);
        return response;
    }
}
