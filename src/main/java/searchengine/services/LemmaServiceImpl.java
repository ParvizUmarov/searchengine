package searchengine.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import searchengine.dto.mapper.LemmaMapper;
import searchengine.dto.model.LemmaDto;
import searchengine.repository.LemmaRepository;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class LemmaServiceImpl implements LemmaService{

    private final LemmaRepository lemmaRepository;
    private final LemmaMapper lemmaMapper;

    @Override
    public List<LemmaDto> findLemmaBySiteId(int siteId) {
        return lemmaRepository
                .findBySiteId(siteId)
                .stream()
                .map(lemmaMapper::toDto)
                .toList();
    }
}
