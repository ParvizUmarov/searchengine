package searchengine.services;

import searchengine.dto.model.IndexResponse;

public interface ManagementService {

    IndexResponse startIndexing();
    IndexResponse stopIndexing();
    IndexResponse addIndexPage(String url);

}
