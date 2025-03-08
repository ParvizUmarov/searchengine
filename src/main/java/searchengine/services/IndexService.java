package searchengine.services;

import searchengine.dto.model.IndexResponse;

public interface IndexService {

    IndexResponse startIndexing();
    IndexResponse stopIndexing();
    IndexResponse addIndexPage(String url);

}
