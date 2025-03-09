package searchengine.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import searchengine.dto.model.IndexErrorResponse;
import searchengine.dto.model.IndexResponse;
import searchengine.dto.model.IndexSuccessResponse;
import searchengine.repository.IndexRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class IndexServiceImpl implements IndexService{

    private final IndexRepository indexRepository;

    @Override
    public IndexResponse startIndexing() {
        log.info("call startIndexing");
        return new IndexSuccessResponse(true);
    }

    @Override
    public IndexResponse stopIndexing() {
        log.info("call stopIndexing");
        return new IndexErrorResponse(false, "Индексация не запущена");
    }

    @Override
    public IndexResponse addIndexPage(String url) {
        log.info("call addIndexPage: url={}", url);
        return null;
    }
}
