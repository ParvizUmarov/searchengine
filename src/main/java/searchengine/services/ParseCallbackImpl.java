package searchengine.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import searchengine.entity.Status;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParseCallbackImpl implements ParseCallback{

    private final SiteService siteService;
    private final PageService pageService;

    @Override
    public void provideSiteInfo(String url, String mainDomain, String body) {
        log.info("handle: {}, {}", url, mainDomain);
        siteService.updateSiteIndexingStatus(null, Status.INDEXING);
        pageService.writeIndexingSiteInfo();

    }
}
