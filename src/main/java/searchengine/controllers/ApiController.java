package searchengine.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import searchengine.dto.model.IndexResponse;
import searchengine.dto.model.SearchRequest;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.services.ManagementService;
import searchengine.services.SearchService;
import searchengine.services.StatisticsService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final StatisticsService statisticsService;
    private final ManagementService managementService;
    private final SearchService searchService;

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public ResponseEntity<IndexResponse> startIndexing() {
        return ResponseEntity.ok(managementService.startIndexing());
    }

    @GetMapping("/stopIndexing")
    public ResponseEntity<IndexResponse> stopIndexing() {
        return ResponseEntity.ok(managementService.stopIndexing());
    }

    @PostMapping("/indexPage")
    public ResponseEntity<IndexResponse> addIndexPage(@Param("url") String url){
        return ResponseEntity.ok(managementService.addIndexPage(url));
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(SearchRequest request){
        return ResponseEntity.ok(searchService.search(request));
    }

}
