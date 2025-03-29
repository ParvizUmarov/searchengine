package searchengine.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import searchengine.dto.mapper.IndexMapper;
import searchengine.dto.model.IndexingSiteInfo;
import searchengine.dto.model.PageDto;
import searchengine.entity.Index;
import searchengine.repository.IndexRepository;
import searchengine.services.IndexService;
import searchengine.services.PageService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService {

    private final IndexRepository indexRepository;
    private final IndexMapper indexMapper;
    private final PageService pageService;

    @Override
    public void add(IndexingSiteInfo info) {

        var page = pageService.findById(info.getPageId());

        if(page != null){
            var indexEntity = Index.builder()
                    .page(page)
                    .lemma(info.getLemma())
                    .ranking(info.getRank())
                    .build();
            var index = indexRepository.save(indexEntity);
        }
    }

    @Override
    public void deleteByPageId(List<PageDto> pages) {
        pages.forEach((p)->{
            indexRepository.deleteByPageId(p.getId());
        });
    }
}
