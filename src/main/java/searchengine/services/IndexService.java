package searchengine.services;

import searchengine.dto.model.IndexingSiteInfo;
import searchengine.dto.model.PageDto;

import java.util.List;

public interface IndexService {

    void add(IndexingSiteInfo info);

    void deleteByPageId(List<PageDto> pages);
}
