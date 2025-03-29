package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import searchengine.entity.Site;

import java.util.Optional;

@Repository
public interface SiteRepository extends JpaRepository<Site, Integer> {

    Optional<Site> findByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM site s WHERE s.url = ?1 LIMIT 1")
    Optional<Site> findByUrl(String url);

}
