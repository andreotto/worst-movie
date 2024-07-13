package com.worstmovie.service;

import com.worstmovie.business.AwardIntervalsCalculator;
import com.worstmovie.entity.Movies;
import com.worstmovie.model.AwardProducerModel;
import com.worstmovie.model.IntervalInfo;
import com.worstmovie.model.MovieModel;
import com.worstmovie.repository.MoviesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AwardMovieService {

    private final MoviesRepository moviesRepository;

    /**
     * Retrieves a list of AwardProducerModel objects representing the intervals
     * between awards for each producer. The intervals are calculated based on a
     * list of MovieModel objects obtained from the movies repository.
     *
     * @return a AwardProducerModel object containing the minimum and maximum intervals
     * for each producer
     * @throws NoSuchElementException if the list of winners obtained from the movies
     *                                repository is empty
     */
    public AwardProducerModel getAwardProducers() {

        List<MovieModel> movieModels = Optional.ofNullable(moviesRepository.findWinners())
                .filter(list -> !list.isEmpty())
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(movie -> new MovieModel(movie.getYear(), movie.getTitle(), movie.getStudio(),
                        movie.getProducer(), movie.getWinner()))
                .collect(Collectors.toList());

        AwardIntervalsCalculator awardInterval = new AwardIntervalsCalculator();
        List<IntervalInfo> intervalInfos = awardInterval.calculateIntervals(movieModels);

        List<IntervalInfo> minInterval = awardInterval.getMinInterval(intervalInfos);
        List<IntervalInfo> maxInternal = awardInterval.getMaxInterval(intervalInfos);

        return new AwardProducerModel(minInterval, maxInternal);
    }

    /**
     * Saves the list of movie models to the movies repository.
     * Converts each movie model to a Movies entity and saves them.
     *
     * @param movies the list of movie models to be saved
     */
    public void saveMovies(List<MovieModel> movies) {

        List<Movies> m = movies.stream()
                .map(movie -> Movies.builder()
                        .year(movie.year())
                        .title(movie.title())
                        .producer(movie.producer())
                        .studio(movie.studio())
                        .winner(movie.winner()).build())
                .toList();


        moviesRepository.saveAll(m);
    }
}
