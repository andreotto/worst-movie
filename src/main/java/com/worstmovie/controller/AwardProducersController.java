package com.worstmovie.controller;

import com.worstmovie.model.AwardProducerModel;
import com.worstmovie.service.AwardMovieService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/award-producers")
@AllArgsConstructor
@Slf4j
public class AwardProducersController {

    private final AwardMovieService awardMovieService;

    @GetMapping
    public AwardProducerModel getAwardMovies() {
        return awardMovieService.getAwardProducers();
    }
}
