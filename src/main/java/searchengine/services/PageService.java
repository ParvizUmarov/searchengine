package searchengine.services;

import searchengine.dto.model.PageDto;
import searchengine.dto.model.SiteProvideDetailInfo;
import searchengine.entity.Page;

import java.util.List;

public interface PageService {

    List<PageDto> findPageBySiteId(int siteId);

    Integer writeIndexingSiteInfo(SiteProvideDetailInfo detailInfo);

    void clearPagesBySiteUrl(int id);

    int getCountOfPagesBySiteId(int siteId);

    Page findById(int id);

}
