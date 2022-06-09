package com.musicstore.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.musicstore.musicstorerecommendations.model.TrackRecommendation;
import com.musicstore.musicstorerecommendations.service.ServiceLayer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackRecommendationController.class)
public class TrackRecommendationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer serviceLayer;

    private ObjectMapper mapper = new ObjectMapper();

    TrackRecommendation inputTrackRec1;
    TrackRecommendation inputTrackRec2;
    TrackRecommendation editedInputRec1;
    TrackRecommendation outputTrackRec1;
    TrackRecommendation outputTrackRec2;
    List<TrackRecommendation> allRecs;

    String inputRecString1;
    String inputRecString2;
    String editedRecString1;
    String outputRecString1;
    String outputRecString2;
    String allRecsString;

    @Before
    public void setUp() throws Exception {
        inputTrackRec1 = new TrackRecommendation(1L, 1L, true);
        inputTrackRec2 = new TrackRecommendation(2L, 1L, true);
        outputTrackRec1 = new TrackRecommendation(1L,1L, 1L, true);
        outputTrackRec2 = new TrackRecommendation(2L,2L, 1L, true);
        editedInputRec1 = outputTrackRec1;
        editedInputRec1.setUserId(2L);
        editedInputRec1.setLiked(false);

        inputRecString1 = mapper.writeValueAsString(inputTrackRec1);
        inputRecString2 = mapper.writeValueAsString(inputTrackRec2);
        outputRecString1 = mapper.writeValueAsString(outputTrackRec1);
        outputRecString2 = mapper.writeValueAsString(outputTrackRec2);
        editedRecString1 = mapper.writeValueAsString(editedInputRec1);
        allRecs = Arrays.asList(new TrackRecommendation[]{outputTrackRec1, outputTrackRec2});
        allRecsString = mapper.writeValueAsString(allRecs);

        when(serviceLayer.createTrackRec(inputTrackRec1)).thenReturn(outputTrackRec1);
        when(serviceLayer.findAllTrackRecs()).thenReturn(allRecs);
        when(serviceLayer.findTrackRecById(2L)).thenReturn(outputTrackRec2);
    }

    @Test
    public void shouldAddTrackRecOnPostRequest() throws Exception {
        mockMvc.perform(post("/recommendations/track")
                        .content(inputRecString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputRecString1));
    }

    @Test
    public void shouldGetArrayOfTrackRecommendations() throws Exception {
        mockMvc.perform(get("/recommendations/track"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allRecsString));
    }

    @Test
    public void shouldGetTrackRecById() throws Exception {
        mockMvc.perform(get("/recommendations/track/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputRecString2));
    }

    @Test
    public void shouldUpdateTrackRec() throws Exception {
        mockMvc.perform(put("/recommendations/track/1")
                        .content(editedRecString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteTrackRec() throws Exception {
        mockMvc.perform(delete("/recommendations/track/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRespondWithBadRequestWhenTrackRecCreateRequestIsBad() throws Exception {
        TrackRecommendation rec1 = new TrackRecommendation();
        rec1.setLiked(true);
        String inputRec1 = mapper.writeValueAsString(rec1);


        mockMvc.perform(post("/recommendations/track")
                        .content(inputRec1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());       // Assert HttpStatus 400 Response

    }

    @Test
    public void shouldReturn404WhenFindingInvalidRecommendationId() throws Exception {
        mockMvc.perform(get("/recommendations/track/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn422WhenPutRequestContainsInvalidIds() throws Exception {
        mockMvc.perform(put("/recommendations/track/999")
                        .content(editedRecString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}