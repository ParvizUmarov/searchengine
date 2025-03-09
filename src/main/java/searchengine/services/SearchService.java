package searchengine.services;

import searchengine.dto.model.SearchRequest;

public interface SearchService {

    Object search(SearchRequest request);

}
