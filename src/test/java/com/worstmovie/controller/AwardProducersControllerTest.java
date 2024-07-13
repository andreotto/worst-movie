package com.worstmovie.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.worstmovie.model.AwardProducerModel;
import com.worstmovie.model.IntervalInfo;
import com.worstmovie.service.AwardMovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AwardProducersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AwardMovieService awardMovieService;

    @Test
    void getAwardMoviesShouldReturnAwardProducerModel() throws Exception {
        //min
        IntervalInfo producer1 = new IntervalInfo("Producer1", 1, 2020, 2021);
        IntervalInfo producer2 = new IntervalInfo("Producer2", 1, 2019, 2019);

        //min
        IntervalInfo producer3 = new IntervalInfo("Producer3", 10, 2010, 2020);
        IntervalInfo producer4 = new IntervalInfo("Producer4", 10, 2010, 2020);

        AwardProducerModel awardProducerModel
                = new AwardProducerModel(List.of(producer1, producer2), List.of(producer3, producer4));
        when(awardMovieService.getAwardProducers()).thenReturn(awardProducerModel);

        MvcResult mvcResult = mockMvc.perform(get("/award-producers")).andReturn();
        String json = mvcResult.getResponse().getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        AwardProducerModel awardProducerModelResult = mapper.readValue(json, AwardProducerModel.class);

        assertEquals(awardProducerModel, awardProducerModelResult);
    }


    @Test
    void getAwardMoviesShouldReturnNoContent() throws Exception {

        when(awardMovieService.getAwardProducers()).thenThrow(NoSuchElementException.class);

        mockMvc.perform(get("/award-producers"))
                .andExpect(status().isNoContent());
    }
}