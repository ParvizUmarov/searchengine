package searchengine.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import searchengine.config.ConnectionSettings;

import java.io.IOException;
import java.util.concurrent.ConcurrentSkipListSet;

import static java.lang.Thread.sleep;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParserServiceImpl implements ParserService{

    private static ConcurrentSkipListSet<String> links;
    private final ParseCallback callback;
    private final ConnectionSettings settings;

    @Override
    public ConcurrentSkipListSet<String> getLinks(String url, String mainDomain) {
        log.info("userAgent: {}", settings.getUserAgent());
        log.info("referrer: {}", settings.getReferrer());

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
            callback.provideSiteInfo(url, mainDomain, body);

            for (Element element : elements) {
                String link = element.absUrl("href");
                if (isLink(link, mainDomain) && !isFile(link)) {
                    links.add(link);
                }
            }

        } catch (IOException e) {
            System.out.println(e + " - " + url);
        }catch (InterruptedException e){
            System.out.println("INTERRUPTED EXCEPTION");
        }finally {
            System.out.println("Time taken: " + (System.currentTimeMillis() - startTime) + "mc -- for " + url);
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
