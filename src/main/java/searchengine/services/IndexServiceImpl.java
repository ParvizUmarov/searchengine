package searchengine.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import searchengine.config.ConnectionSettings;
import searchengine.config.SitesList;
import searchengine.dto.model.IndexErrorResponse;
import searchengine.dto.model.IndexResponse;
import searchengine.dto.model.IndexSuccessResponse;
import searchengine.dto.model.SiteMap;
import searchengine.entity.Status;
import searchengine.repository.IndexRepository;
import searchengine.repository.PageRepository;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService {

    private final IndexRepository indexRepository;
    private final SiteService siteService;
    private final SitesList sitesList;
    private final PageRepository pageRepository;
    private final ParseCallback parseCallback;
    private final ConnectionSettings connectionSettings;
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    @Override
    public IndexResponse startIndexing() {
        log.info("call startIndexing");

        var parseHtml = new ParserServiceImpl(parseCallback, connectionSettings);

        for (var site : sitesList.getSites()) {
            var findSite = siteService.getSiteByName(site.getName());

            if (findSite != null) {
                pageRepository.deleteBySiteId(findSite.getId());
                siteService.deleteById(findSite.getId());
            }

            var savedSite = siteService.createSite(site);
            log.info("START INDEXING FOR SITE: {}", site);

            //TODO parsing all page from site in table page
            var siteMap = new SiteMap(site.getUrl());
            var task = new SiteMapRecursiveAction(siteMap, parseHtml, siteMap.getUrl());
            forkJoinPool.execute(task);

            //TODO during process all time update field date in status_tine in site table


            //TODO update indexing and update status of indexed site
//            try {
//                Thread.sleep(5);
//                savedSite.setStatus(Status.INDEXED);
//                var indexedSite = siteRepository.save(savedSite);
//                log.info("SAVED INDEXED site: {}", indexedSite);
//            } catch (InterruptedException e) {
//            }

        }

        forkJoinPool.shutdown();

        try {
            forkJoinPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            log.info("Thread error: {}", e.getMessage());
            siteService.updateSiteIndexingStatus("Индексация приостановлена", Status.FAILED);
        }


        return new IndexSuccessResponse(true);
    }

    @Override
    public IndexResponse stopIndexing() {
        log.info("call stopIndexing");

        if (forkJoinPool.isQuiescent()) {
            forkJoinPool.shutdown();

            if (forkJoinPool.isShutdown()) {
                siteService.updateSiteIndexingStatus("Индексация приостановлена", Status.FAILED);
                return new IndexSuccessResponse(true);
            } else {
                return new IndexErrorResponse(false, "Индексация не запущена");
            }
        } else {
            return new IndexErrorResponse(false, "Индексация не запущена");
        }

    }

    @Override
    public IndexResponse addIndexPage(String url) {
        log.info("call addIndexPage: url={}", url);
        return null;
    }
}
