package searchengine.services;

import searchengine.dto.model.LemmaDto;

import java.util.List;

public interface LemmaService {

    List<LemmaDto> findLemmaBySiteId(int siteId);

}
