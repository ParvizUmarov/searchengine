package searchengine.services;

import searchengine.dto.model.PageDto;
import java.util.List;

public interface PageService {

    List<PageDto> findPageBySiteId (int siteId);
    void writeIndexingSiteInfo();

}
