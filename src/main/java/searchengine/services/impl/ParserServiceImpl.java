package searchengine.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import searchengine.config.ConnectionSettings;
import searchengine.dto.model.SiteProvideDetailInfo;
import searchengine.services.ParseCallback;
import searchengine.services.ParserService;

import java.io.IOException;
import java.util.concurrent.ConcurrentSkipListSet;

import static java.lang.Thread.sleep;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParserServiceImpl implements ParserService {

    private static ConcurrentSkipListSet<String> links;
    private final ParseCallback callback;
    private final ConnectionSettings settings;

    @Override
    public ConcurrentSkipListSet<String> getLinks(String url, String mainDomain) {
        links = new ConcurrentSkipListSet<>();
        long startTime = System.currentTimeMillis();

        try {
            sleep(1000);
            Connection connection = Jsoup.connect(url)
                    .userAgent(settings.getReferrer())
                    .referrer(settings.getReferrer())
                    .ignoreHttpErrors(true)
                    .timeout(10000000)
                    .followRedirects(false);

            Document document = connection.get();
            Elements elements = document.select("body").select("a");

            var body = document.body().html();
            var statusCode = connection.execute().statusCode();
            callback.provideSiteInfo(new SiteProvideDetailInfo(
                    url, mainDomain, body, statusCode, document.body().text()));

            for (Element element : elements) {
                String link = element.absUrl("href");
                if (isLink(link, mainDomain) && !isFile(link)) {
                    links.add(link);
                }
            }

        } catch (IOException e) {
            log.error("{} - {}", e, url);
        }catch (InterruptedException e){
            log.error("INTERRUPTED EXCEPTION");
        }finally {
            log.info("Time taken: {}mc -- for {}", System.currentTimeMillis() - startTime, url);
        }
        return links;
    }

    private boolean isLink(String link, String mainDomain) {
        return link.startsWith(mainDomain);
    }

    private boolean isFile(String link) {
        link.toLowerCase();
        return link.contains(".jpg")
                || link.contains(".jpeg")
                || link.contains(".png")
                || link.contains(".gif")
                || link.contains(".webp")
                || link.contains(".pdf")
                || link.contains(".eps")
                || link.contains(".xlsx")
                || link.contains(".doc")
                || link.contains(".pptx")
                || link.contains(".docx")
                || link.contains("?_ga");
    }

}
