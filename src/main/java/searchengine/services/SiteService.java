package searchengine.services;

import searchengine.config.SiteInfo;
import searchengine.dto.model.SiteDto;
import searchengine.entity.Site;
import searchengine.entity.Status;

public interface SiteService {

    SiteDto getSiteByName(String name);
    void updateSiteIndexingStatus(String lastError, Status status);
    SiteDto createSite(SiteInfo siteInfo);
    void deleteById(int id);


}
