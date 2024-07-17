package com.worstmovie.business;

import com.worstmovie.model.IntervalInfo;
import com.worstmovie.model.MovieModel;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Stream;

@Component
public class AwardIntervalsCalculator {

    public List<MovieModel> separateList(List<MovieModel> movieModels) {
        List<String> winnersproducer = getWinners(movieModels);
        return getFilteredMovies(movieModels, winnersproducer);
    }

    public List<IntervalInfo> calculateIntervals(List<MovieModel> winners) {
        Map<String, List<Integer>> winnerYears = getWinnersYears(winners);

        List<IntervalInfo> infoList = new ArrayList<>();
        for (var entry : winnerYears.entrySet()) {
            String producer = entry.getKey();
            List<Integer> years = entry.getValue();
            Collections.sort(years);
            addIntervalInfos(infoList, producer, years);
        }
        return infoList;
    }

    public List<IntervalInfo> getMinInterval(List<IntervalInfo> intervalInfos) {
        int minInterval = intervalInfos.stream().min(Comparator.comparing(IntervalInfo::interval)).get().interval();
        return filterInfoByInterval(intervalInfos, minInterval);
    }

    public List<IntervalInfo> getMaxInterval(List<IntervalInfo> intervalInfos) {
        int maxInterval = intervalInfos.stream().max(Comparator.comparing(IntervalInfo::interval)).get().interval();
        return filterInfoByInterval(intervalInfos, maxInterval);
    }

    private List<String> getProducers(MovieModel movie) {
        return Stream.of(movie.producer().split(",|and"))
                .map(String::trim)
                .toList();
    }

    private List<String> getWinners(List<MovieModel> movieModels) {
        Map<String, Boolean> producerAppearance = new HashMap<>();
        for (MovieModel movie : movieModels) {
            List<String> producers = getProducers(movie);
            for (String producer : producers) {
                producerAppearance.put(producer, producerAppearance.containsKey(producer));
            }
        }
        return producerAppearance.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(Map.Entry::getKey)
                .toList();
    }

    private List<MovieModel> getFilteredMovies(List<MovieModel> movieModels, List<String> winnersproducer) {
        List<MovieModel> filteredMovies = new ArrayList<>();
        for (MovieModel movie : movieModels) {
            List<String> producers = getProducers(movie);
            for (String s : winnersproducer) {
                if (producers.contains(s)) {
                    filteredMovies.add(new MovieModel(movie.year(), movie.title(), movie.studio(), s, true));
                }
            }
        }
        return filteredMovies;
    }


    private Map<String, List<Integer>> getWinnersYears(List<MovieModel> winners) {
        Map<String, List<Integer>> winnerYears = new HashMap<>();

        for (MovieModel winner : winners) {
            List<Integer> years = winnerYears.getOrDefault(winner.producer(), new ArrayList<>());
            years.add(winner.year());
            winnerYears.put(winner.producer(), years);
        }

        return winnerYears;
    }

    private void addIntervalInfos(List<IntervalInfo> infoList, String producer, List<Integer> years) {
        for (int i = 0; i < years.size() - 1; i++) {
            int interval = years.get(i + 1) - years.get(i);
            infoList.add(new IntervalInfo(producer, interval, years.get(i), years.get(i + 1)));
        }
    }


    private List<IntervalInfo> filterInfoByInterval(List<IntervalInfo> intervalInfos, int interval) {
        return intervalInfos.stream()
                .filter(record -> record.interval() == interval)
                .toList();
    }
}