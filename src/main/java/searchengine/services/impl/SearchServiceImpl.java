package searchengine.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import searchengine.dto.model.SearchRequest;
import searchengine.exception.CustomException;
import searchengine.services.SearchService;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchServiceImpl implements SearchService {

    @Override
    public Object search(SearchRequest request) {
        log.info("call search method, {}", request);

        if(request.getQuery().isBlank()){
            throw new CustomException("Задан пустой поисковый запрос");
        }
        throw new CustomException("Указанная страница не найдена");
    }
}
