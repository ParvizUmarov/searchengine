package searchengine.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import searchengine.config.SitesList;
import searchengine.dto.model.IndexErrorResponse;
import searchengine.dto.model.IndexResponse;
import searchengine.dto.model.IndexSuccessResponse;
import searchengine.dto.model.SiteMap;
import searchengine.entity.Status;
import searchengine.services.*;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagementServiceImpl implements ManagementService {

    private final SiteService siteService;
    private final SitesList sitesList;
    private final PageService pageService;
    private final ParserService parserService;
    private ForkJoinPool forkJoinPool = null;
    private final IndexService indexService;
    private final LemmaService lemmaService;

    @Override
    @Transactional
    public IndexResponse startIndexing() {

        if (forkJoinPool != null)
            return new IndexErrorResponse(false, "Индексация уже запущена");

        try {
            log.info("call startIndexing");
            startParsing();
        } catch (Exception e) {
            log.info("Thread error: {}", e.getMessage());
            stopIndexing();
        }

        return new IndexSuccessResponse(true);
    }

    private void startParsing() {
        forkJoinPool = new ForkJoinPool(sitesList.getSites().size());

        for (var site : sitesList.getSites()) {
            clearIndexingSiteFromDb(site.getUrl());

            var savedSite = siteService.saveSite(site);
            log.info("START INDEXING FOR SITE: {}", savedSite);

            var siteMap = new SiteMap(site.getUrl());
            var task = new SiteMapRecursiveAction(siteMap, parserService, siteMap.getUrl());
            forkJoinPool.execute(task);
        }

        new Thread(() -> {
            forkJoinPool.awaitQuiescence(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            shutdownForkJoinPool();
        }).start();

    }

    private void shutdownForkJoinPool() {
        try {
            if (forkJoinPool != null) {
                forkJoinPool.shutdown();
                forkJoinPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                log.info("finish indexing sites");
            }

            siteService.updateSiteFinishedIndexing();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearIndexingSiteFromDb(String url){
        var foundSite = siteService.findSiteByUrl(url);

        if(foundSite != null) {
            var pages = pageService.findPageBySiteId(foundSite.getId());
            var siteId = foundSite.getId();
            indexService.deleteByPageId(pages);
            lemmaService.deleteBySiteId(siteId);
            pageService.clearPagesBySiteUrl(siteId);
            siteService.deleteById(siteId);
            log.info("Clear all indexing site info from every table in db");
        }
    }


    @Override
    public IndexResponse stopIndexing() {
        log.info("call stopIndexing");

        if (forkJoinPool != null) {
            forkJoinPool.shutdownNow();
            Thread.currentThread().interrupt();
            siteService.updateSiteIndexingStatus("Индексация приостановлена пользователем", Status.FAILED);
            forkJoinPool = null;
            return new IndexSuccessResponse(true);
        } else {
            return new IndexErrorResponse(false, "Индексация не запущена");
        }
    }

    @Override
    public IndexResponse addIndexPage(String url) {
        log.info("call addIndexPage: url={}", url);

        var isValid = siteService.checkUrl(url);

        return isValid
                ? new IndexSuccessResponse(true)
                : new IndexErrorResponse(false, "Данная страница находится за пределами сайтов, \n" +
                "указанных в конфигурационном файле\n");
    }
}
