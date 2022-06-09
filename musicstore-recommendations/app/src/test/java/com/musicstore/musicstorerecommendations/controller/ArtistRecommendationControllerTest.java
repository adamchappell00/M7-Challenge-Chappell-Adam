package com.musicstore.musicstorerecommendations.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicstore.musicstorerecommendations.model.ArtistRecommendation;
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

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ArtistRecommendationController.class)
public class ArtistRecommendationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer serviceLayer;

    private ObjectMapper mapper = new ObjectMapper();

    ArtistRecommendation inputArtistRec1;
    ArtistRecommendation inputArtistRec2;
    ArtistRecommendation editedInputRec1;
    ArtistRecommendation outputArtistRec1;
    ArtistRecommendation outputArtistRec2;
    List<ArtistRecommendation> allRecs;

    String inputRecString1;
    String inputRecString2;
    String editedRecString1;
    String outputRecString1;
    String outputRecString2;
    String allRecsString;

    @Before
    public void setUp() throws Exception {
        inputArtistRec1 = new ArtistRecommendation(1L, 1L, true);
        inputArtistRec2 = new ArtistRecommendation(2L, 1L, true);
        outputArtistRec1 = new ArtistRecommendation(1L,1L, 1L, true);
        outputArtistRec2 = new ArtistRecommendation(2L,2L, 1L, true);
        editedInputRec1 = outputArtistRec1;
        editedInputRec1.setUserId(2L);
        editedInputRec1.setLiked(false);

        inputRecString1 = mapper.writeValueAsString(inputArtistRec1);
        inputRecString2 = mapper.writeValueAsString(inputArtistRec2);
        outputRecString1 = mapper.writeValueAsString(outputArtistRec1);
        outputRecString2 = mapper.writeValueAsString(outputArtistRec2);
        editedRecString1 = mapper.writeValueAsString(editedInputRec1);
        allRecs = Arrays.asList(new ArtistRecommendation[]{outputArtistRec1, outputArtistRec2});
        allRecsString = mapper.writeValueAsString(allRecs);

        when(serviceLayer.createArtistRec(inputArtistRec1)).thenReturn(outputArtistRec1);
        when(serviceLayer.findAllArtistRecs()).thenReturn(allRecs);
        when(serviceLayer.findArtistRecById(2L)).thenReturn(outputArtistRec2);
    }

    @Test
    public void shouldAddArtistRecOnPostRequest() throws Exception {
        mockMvc.perform(post("/recommendations/artist")
                        .content(inputRecString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputRecString1));
    }

    @Test
    public void shouldGetArrayOfArtistRecommendations() throws Exception {
        mockMvc.perform(get("/recommendations/artist"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allRecsString));
    }

    @Test
    public void shouldGetArtistRecById() throws Exception {
        mockMvc.perform(get("/recommendations/artist/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputRecString2));
    }

    @Test
    public void shouldUpdateArtistRec() throws Exception {
        mockMvc.perform(put("/recommendations/artist/1")
                        .content(editedRecString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteArtistRec() throws Exception {
        mockMvc.perform(delete("/recommendations/artist/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRespondWithBadRequestWhenArtistRecCreateRequestIsBad() throws Exception {
        ArtistRecommendation rec1 = new ArtistRecommendation();
        rec1.setLiked(true);
        String inputRec1 = mapper.writeValueAsString(rec1);


        mockMvc.perform(post("/recommendations/artist")
                        .content(inputRec1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());       // Assert HttpStatus 400 Response

    }

    @Test
    public void shouldReturn404WhenFindingInvalidRecommendationId() throws Exception {
        mockMvc.perform(get("/recommendations/artist/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn422WhenPutRequestContainsInvalidIds() throws Exception {
        mockMvc.perform(put("/recommendations/artist/999")
                        .content(editedRecString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}