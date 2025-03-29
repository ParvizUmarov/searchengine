package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import searchengine.entity.Lemma;

import java.util.List;
import java.util.Optional;

@Repository
public interface LemmaRepository extends JpaRepository<Lemma, Integer> {

    List<Lemma> findBySiteId(int siteId);

    Optional<Lemma> findByLemma(String lemma);

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM lemma l WHERE l.site_id = ?1")
    void deleteBySiteId(int siteId);

}
