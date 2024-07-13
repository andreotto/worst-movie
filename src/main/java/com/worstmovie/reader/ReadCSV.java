package com.worstmovie.reader;

import com.worstmovie.model.MovieModel;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
public class ReadCSV {

    public static List<MovieModel> readCSV(Path path) {

        try {
            List<String[]> lines = new ArrayList<>();
            List<String> fileLines = Files.readAllLines(path);

            // Remove the first line
            fileLines.removeFirst();

            // Parse the lines
            for (String line : fileLines) {
                String[] values = line.split(";");
                lines.add(values);
            }

            List<MovieModel> movies = new ArrayList<>();
            for (String[] values : lines) {
                Integer year = Integer.parseInt(values[0]);
                String title = values[1];
                String studio = values[2];
                String producer = values[3];
                Boolean winner = (values.length > 4) ? getBooleanValue(values[4]) : Boolean.FALSE;
                movies.add(new MovieModel(year, title, studio, producer, winner));
            }
            return movies;
        } catch (Exception e) {
            throw new RuntimeException("Error when trying to parse the csv file to object");
        }

    }

    private static Boolean getBooleanValue(String value) {
        return value != null && Objects.equals(value, "yes");
    }

}
