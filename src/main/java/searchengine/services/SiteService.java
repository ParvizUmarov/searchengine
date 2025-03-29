package searchengine.services;

import searchengine.config.SiteInfo;
import searchengine.dto.model.SiteDto;
import searchengine.entity.Status;

public interface SiteService {

    SiteDto findByName(String name);

    void updateSiteIndexingStatus(String lastError, Status status);

    SiteDto saveSite(SiteInfo siteInfo);

    void deleteById(int id);

    SiteDto findSiteByUrl(String url);

    void updateSiteFinishedIndexing();

    boolean checkUrl(String url);
}
