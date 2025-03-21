package searchengine.dto.model;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.concurrent.CopyOnWriteArrayList;

@RequiredArgsConstructor
public class SiteMap {
    private final String url;
    private final CopyOnWriteArrayList<SiteMap> siteMapChildren= new CopyOnWriteArrayList<>();

    public void addChildren(SiteMap children) {
        siteMapChildren.add(children);
    }

    public CopyOnWriteArrayList<SiteMap> getSiteMapChildren() {
        return siteMapChildren;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "SiteMap{" +
                "url='" + url + '\'' +
                ", siteMapChildren=" + siteMapChildren +
                '}';
    }
}
