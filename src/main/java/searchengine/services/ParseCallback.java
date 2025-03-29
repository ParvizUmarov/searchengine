package searchengine.services;

import searchengine.dto.model.SiteProvideDetailInfo;

public interface ParseCallback {

    void provideSiteInfo(SiteProvideDetailInfo detailInfo);
}
