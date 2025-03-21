package searchengine.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import searchengine.dto.model.SiteMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.RecursiveAction;

@Slf4j
@RequiredArgsConstructor
public class SiteMapRecursiveAction extends RecursiveAction {
    private final SiteMap siteMap;
    private final ParserService parserService;
    private final String mainDomain;

    private static final ConcurrentSkipListSet<String> linksPool = new ConcurrentSkipListSet<>();

    @Override
    protected void compute() {
        linksPool.add(siteMap.getUrl());

        ConcurrentSkipListSet<String> links = parserService.getLinks(siteMap.getUrl(), mainDomain);
        for (String link : links) {
            if (!linksPool.contains(link)) {
                linksPool.add(link);
                siteMap.addChildren(new SiteMap(link));

            }
        }

        List<SiteMapRecursiveAction> taskList = new ArrayList<>();
        for (SiteMap child : siteMap.getSiteMapChildren()) {
            SiteMapRecursiveAction task = new SiteMapRecursiveAction(child, parserService, mainDomain);
            task.fork();
            taskList.add(task);
        }

        for (SiteMapRecursiveAction task : taskList) {
            task.join();
        }
    }
}
