package searchengine.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import searchengine.config.SiteInfo;
import searchengine.config.SitesList;
import searchengine.dto.mapper.SiteMapper;
import searchengine.dto.model.SiteDto;
import searchengine.entity.Site;
import searchengine.entity.Status;
import searchengine.exception.ResourceNotFoundException;
import searchengine.repository.SiteRepository;

import java.sql.Date;
import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class SiteServiceImpl implements SiteService{

    private final SiteRepository siteRepository;
    private final SitesList sitesList;
    private final SiteMapper siteMapper;

    @Override
    public SiteDto getSiteByName(String name) {
        return siteRepository
                .findByName(name)
                .map(siteMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Site " + name + " not found"));
    }

    @Override
    public void updateSiteIndexingStatus(String lastError, Status status) {
        siteRepository.findAll().forEach((site) -> {
            if(site.getStatus() == Status.INDEXING){
                site.setStatus(status);
                site.setLastError(lastError);
                site.setDate(Date.from(Instant.now()));
                siteRepository.save(site);
            }
        });

    }

    @Override
    public SiteDto createSite(SiteInfo site) {
        var newEntity = siteRepository.save(
                Site.builder()
                        .name(site.getName())
                        .url(site.getUrl())
                        .date(Date.from(Instant.now()))
                        .status(Status.INDEXING)
                        .build());
        return siteMapper.toDto(newEntity);
    }

    @Override
    public void deleteById(int id) {
        siteRepository.deleteById(id);
    }


}
