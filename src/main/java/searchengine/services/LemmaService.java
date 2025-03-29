package searchengine.services;

import searchengine.dto.model.LemmaDto;

import java.util.List;

public interface LemmaService {

    List<LemmaDto> findLemmaBySiteId(int siteId);

    void savedLemmas(String siteBodyText, String url, int pageId);

    void deleteBySiteId(int siteId);
}
