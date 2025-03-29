package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import searchengine.entity.Page;

import java.util.List;
import java.util.Optional;

@Repository
public interface PageRepository extends JpaRepository<Page, Integer> {

    int deleteBySiteId(int siteId);

    List<Page> findBySiteId(int siteId);

    Optional<Page> findByPath(String path);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM page p WHERE p.site_id = ?1")
    int pagesCountBySiteId(int siteId);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM page p WHERE p.site_id = ?1")
    void deletePageBySiteId(int siteId);




}
