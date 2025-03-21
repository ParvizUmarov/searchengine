package searchengine.services;

import java.util.concurrent.ConcurrentSkipListSet;

public interface ParserService {

    ConcurrentSkipListSet<String> getLinks(String url, String mainDomain);

}
