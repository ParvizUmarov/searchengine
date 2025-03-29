package searchengine.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import searchengine.dto.mapper.LemmaMapper;
import searchengine.dto.model.IndexingSiteInfo;
import searchengine.dto.model.LemmaDto;
import searchengine.repository.LemmaRepository;
import searchengine.services.IndexService;
import searchengine.services.LemmaFinder;
import searchengine.services.LemmaService;
import searchengine.services.SiteService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LemmaServiceImpl implements LemmaService {

    private final LemmaRepository lemmaRepository;
    private final LemmaMapper lemmaMapper;
    private final SiteService siteService;
    private final LemmaFinder lemmaFinder;
    private final IndexService indexService;

    @Override
    public List<LemmaDto> findLemmaBySiteId(int siteId) {
        return lemmaRepository
                .findBySiteId(siteId)
                .stream()
                .map(lemmaMapper::toDto)
                .toList();
    }

    @Override
    //@Transactional
    public void savedLemmas(String siteBodyText, String url, int pageId) {
        var lemmasAndFrequency = lemmaFinder.getLemmasAndFrequency(siteBodyText);
        var site = siteService.findSiteByUrl(url);

        if (site == null) {
            return;
        }

        for (var lemma : lemmasAndFrequency.entrySet()) {
            var lemmaDto = LemmaDto.builder()
                    .lemma(lemma.getKey())
                    .siteId(site.getId())
                    .frequency(lemma.getValue());

            var lemmaFromDb = lemmaRepository.findByLemma(lemma.getKey());

            if (lemmaFromDb.isPresent()) {
                lemmaDto.id(lemmaFromDb.get().getId());
                lemmaDto.frequency(lemmaFromDb.get().getFrequency() + 1);
            } else {
                lemmaDto.frequency(1);
            }

            var savedLemma = lemmaRepository.save(lemmaMapper.toEntity(lemmaDto.build()));
            indexService.add(new IndexingSiteInfo(pageId, savedLemma, lemma.getValue()));
        }

    }

    @Override
    public void deleteBySiteId(int siteId) {
        lemmaRepository.deleteBySiteId(siteId);
    }
}
