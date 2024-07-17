package com.worstmovie.service;

import com.worstmovie.business.AwardIntervalsCalculator;
import com.worstmovie.converter.MovieConverter;
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

@Service
@RequiredArgsConstructor
@Slf4j
public class AwardMovieService {

    private final AwardIntervalsCalculator awardIntervalCalculator;
    private final MoviesRepository moviesRepository;
    private final MovieConverter movieConverter;

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

        List<MovieModel> movieModels = getMovieWinners();

        List<MovieModel> moviesAwards = awardIntervalCalculator.separateList(movieModels);

        List<IntervalInfo> intervalInfos = awardIntervalCalculator.calculateIntervals(moviesAwards);

        List<IntervalInfo> minInterval = awardIntervalCalculator.getMinInterval(intervalInfos);
        List<IntervalInfo> maxInternal = awardIntervalCalculator.getMaxInterval(intervalInfos);

        return new AwardProducerModel(minInterval, maxInternal);
    }

    private List<MovieModel> getMovieWinners() {
        return Optional.ofNullable(moviesRepository.findMoviesByWinnerIsTrue())
                .filter(list -> !list.isEmpty())
                .orElseThrow(NoSuchElementException::new)
                .stream()
                .map(movieConverter::convertToMovieModel)
                .toList();
    }

    /**
     * Saves the list of movie models to the movies repository.
     * Converts each movie model to a Movies entity and saves them.
     *
     * @param movies the list of movie models to be saved
     */
    public void saveMovies(List<MovieModel> movies) {
        List<Movies> entityMovies = movieConverter.convertToEntityMovies(movies);
        moviesRepository.saveAll(entityMovies);
    }

}
