package com.worstmovie;

import com.worstmovie.model.MovieModel;
import com.worstmovie.reader.ReadCSV;
import com.worstmovie.service.AwardMovieService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@SpringBootApplication
public class WorstMovieApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorstMovieApplication.class, args);
    }

    @Bean
    ApplicationRunner applicationRunner(AwardMovieService awardMovieService) {
        return args -> {

            Path path = Paths.get(Objects.requireNonNull(
                    ReadCSV.class.getResource("/changedmovielist.csv")).toURI());

            List<MovieModel> movieModels = ReadCSV.readCSV(path);
            awardMovieService.saveMovies(movieModels);
        };

    }

}
