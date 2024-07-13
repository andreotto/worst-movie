package com.worstmovie.repository;

import com.worstmovie.entity.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MoviesRepository extends JpaRepository<Movies, Long> {


    @Query(value = """
            SELECT *
            FROM movies
            WHERE winner = true
            AND producer IN (
              SELECT producer
              FROM movies
              WHERE winner = true
              GROUP BY producer
              HAVING COUNT(*) > 1
            )
            """, nativeQuery = true)
    List<Movies> findWinners();
}