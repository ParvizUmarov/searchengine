package searchengine.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import searchengine.dto.model.SiteProvideDetailInfo;
import searchengine.entity.Status;
import searchengine.services.LemmaService;
import searchengine.services.PageService;
import searchengine.services.ParseCallback;
import searchengine.services.SiteService;

@Slf4j
@Component
@RequiredArgsConstructor
public class ParseCallbackImpl implements ParseCallback {

    private final SiteService siteService;
    private final PageService pageService;
    private final LemmaService lemmaService;

    @Override
    //@Transactional
    public void provideSiteInfo(SiteProvideDetailInfo detailInfo) {
        siteService.updateSiteIndexingStatus(null, Status.INDEXING);
        var pageId = pageService.writeIndexingSiteInfo(detailInfo);

        if(pageId != 0)
            lemmaService.savedLemmas(detailInfo.getBodyText(), detailInfo.getMainDomain(), pageId);

    }
}
