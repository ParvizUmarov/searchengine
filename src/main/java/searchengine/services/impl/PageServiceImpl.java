package searchengine.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import searchengine.dto.mapper.PageMapper;
import searchengine.dto.model.PageDto;
import searchengine.dto.model.SiteProvideDetailInfo;
import searchengine.entity.Page;
import searchengine.repository.PageRepository;
import searchengine.services.PageService;
import searchengine.services.SiteService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PageServiceImpl implements PageService {

    private final PageRepository pageRepository;
    private final PageMapper pageMapper;
    private final SiteService siteService;

    @Override
    public List<PageDto> findPageBySiteId(int siteId) {
        return pageRepository
                .findBySiteId(siteId)
                .stream()
                .map(pageMapper::toDto)
                .toList();
    }

    @Override
    public Integer writeIndexingSiteInfo(SiteProvideDetailInfo detailInfo) {
        var path = detailInfo.getUrl().replace(detailInfo.getMainDomain(), "");
        var site = siteService.findSiteByUrl(detailInfo.getMainDomain());

        if (site != null) {

            var foundedPage = pageRepository.findByPath(path);

            if (foundedPage.isEmpty()) {
                var page = PageDto.builder()
                        .siteId(site.getId())
                        .code(detailInfo.getCode())
                        .path(path)
                        .content(detailInfo.getBody())
                        .build();

                var entity = pageMapper.toEntity(page);
                var savedPage = pageRepository.save(entity);
                return savedPage.getId();
            }
        }
        return 0;
    }

    @Override
    public void clearPagesBySiteUrl(int id) {
        log.info("clear page by site url={}", id);
        pageRepository.deletePageBySiteId(id);
    }

    @Override
    public int getCountOfPagesBySiteId(int siteId) {
        return pageRepository.pagesCountBySiteId(siteId);
    }

    @Override
    public Page findById(int id) {
        return pageRepository.findById(id).orElse(null);
    }

}
