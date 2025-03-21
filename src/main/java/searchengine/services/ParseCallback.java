package searchengine.services;

public interface ParseCallback {

    void provideSiteInfo(String url, String mainDomain, String body);
}
