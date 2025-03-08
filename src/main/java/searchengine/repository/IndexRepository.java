package searchengine.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import searchengine.entity.Index;

public interface IndexRepository extends JpaRepository<Index, Integer> {
}
