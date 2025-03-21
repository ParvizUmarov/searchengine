package searchengine.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import searchengine.dto.mapper.PageMapper;
import searchengine.dto.model.PageDto;
import searchengine.entity.Page;
import searchengine.repository.PageRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PageServiceImpl implements PageService{

    private final PageRepository pageRepository;
    private final PageMapper pageMapper;


    @Override
    public List<PageDto> findPageBySiteId(int siteId) {
        return pageRepository
                .findBySiteId(siteId)
                .stream()
                .map(pageMapper::toDto)
                .toList();
    }

    @Override
    public void writeIndexingSiteInfo() {

    }
}
