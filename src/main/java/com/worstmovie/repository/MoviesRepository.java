package com.worstmovie.repository;

import com.worstmovie.entity.Movies;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoviesRepository extends JpaRepository<Movies, Long> {

    List<Movies> findMoviesByWinnerIsTrue();
}