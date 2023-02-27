package com.TwitterClone.ProjectBackend.Repository;

import com.TwitterClone.ProjectBackend.Model.Trend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrendRepository extends JpaRepository<Trend, Integer> {
    @Query(value =  "SELECT * " +
            "FROM \"trend\" " +
            "ORDER BY \"num_tweets\" DESC",
            nativeQuery = true)
    Page<Trend> findAll(Pageable page);
}
