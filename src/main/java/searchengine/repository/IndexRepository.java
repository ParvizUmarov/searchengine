package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import searchengine.entity.Index;

public interface IndexRepository extends JpaRepository<Index, Integer> {

    @Modifying
    @Query(nativeQuery = true, value = "DELETE FROM search_index si WHERE si.page_id = ?1")
    void deleteByPageId(int id);

}
