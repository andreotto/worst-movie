package com.worstmovie.business;

import com.worstmovie.model.IntervalInfo;
import com.worstmovie.model.MovieModel;

import java.util.*;

public class AwardIntervalsCalculator {

    public List<IntervalInfo> calculateIntervals(List<MovieModel> winners) {

        Map<String, List<Integer>> winnerYears = new HashMap<>();
        List<IntervalInfo> infoList = new ArrayList<>();

        for (MovieModel winner : winners) {
            List<Integer> years = winnerYears.getOrDefault(winner.producer(), new ArrayList<>());
            years.add(winner.year());
            winnerYears.put(winner.producer(), years);
        }

        for (var entry : winnerYears.entrySet()) {
            String producer = entry.getKey();
            List<Integer> years = entry.getValue();
            Collections.sort(years);

            for (int i = 0; i < years.size() - 1; i++) {
                int interval = years.get(i + 1) - years.get(i);
                infoList.add(new IntervalInfo(producer, interval, years.get(i), years.get(i + 1)));
            }
        }
        return infoList;
    }

    public List<IntervalInfo> getMinInterval(List<IntervalInfo> intervalInfos) {
        int minInterval = intervalInfos.stream()
                .min(Comparator.comparing(IntervalInfo::interval))
                .map(IntervalInfo::interval)
                .orElse(0);

        if (minInterval == 0) return Collections.emptyList();

        return intervalInfos.stream()
                .filter(record -> record.interval() == minInterval)
                .toList();
    }

    public List<IntervalInfo> getMaxInterval(List<IntervalInfo> intervalInfos) {
        int maxInterval = intervalInfos.stream()
                .max(Comparator.comparing(IntervalInfo::interval))
                .map(IntervalInfo::interval)
                .orElse(0);

        if (maxInterval == 0) return Collections.emptyList();

        return intervalInfos.stream()
                .filter(record -> record.interval() == maxInterval)
                .toList();
    }
}
