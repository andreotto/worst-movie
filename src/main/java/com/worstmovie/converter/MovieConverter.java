package com.worstmovie.converter;

import com.worstmovie.entity.Movies;
import com.worstmovie.model.MovieModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MovieConverter {

    public List<Movies> convertToEntityMovies(List<MovieModel> movies) {
        return movies.stream()
                .map(movie -> Movies.builder()
                        .year(movie.year())
                        .title(movie.title())
                        .producer(movie.producer())
                        .studio(movie.studio())
                        .winner(movie.winner()).build())
                .toList();
    }

    public MovieModel convertToMovieModel(Movies movie) {
        return new MovieModel(movie.getYear(), movie.getTitle(), movie.getStudio(),
                movie.getProducer(), movie.getWinner());
    }
}
