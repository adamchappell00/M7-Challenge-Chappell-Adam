package com.musicstore.musicstorerecommendations.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicstore.musicstorerecommendations.model.AlbumRecommendation;
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
@WebMvcTest(AlbumRecommendationController.class)
public class AlbumRecommendationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer serviceLayer;

    private ObjectMapper mapper = new ObjectMapper();

    AlbumRecommendation inputAlbumRec1;
    AlbumRecommendation inputAlbumRec2;
    AlbumRecommendation editedInputRec1;
    AlbumRecommendation outputAlbumRec1;
    AlbumRecommendation outputAlbumRec2;
    List<AlbumRecommendation> allRecs;

    String inputRecString1;
    String inputRecString2;
    String editedRecString1;
    String outputRecString1;
    String outputRecString2;
    String allRecsString;

    @Before
    public void setUp() throws Exception {
        inputAlbumRec1 = new AlbumRecommendation(1L, 1L, true);
        inputAlbumRec2 = new AlbumRecommendation(2L, 1L, true);
        outputAlbumRec1 = new AlbumRecommendation(1L,1L, 1L, true);
        outputAlbumRec2 = new AlbumRecommendation(2L,2L, 1L, true);
        editedInputRec1 = outputAlbumRec1;
        editedInputRec1.setUserId(2L);
        editedInputRec1.setLiked(false);

        inputRecString1 = mapper.writeValueAsString(inputAlbumRec1);
        inputRecString2 = mapper.writeValueAsString(inputAlbumRec2);
        outputRecString1 = mapper.writeValueAsString(outputAlbumRec1);
        outputRecString2 = mapper.writeValueAsString(outputAlbumRec2);
        editedRecString1 = mapper.writeValueAsString(editedInputRec1);
        allRecs = Arrays.asList(new AlbumRecommendation[]{outputAlbumRec1, outputAlbumRec2});
        allRecsString = mapper.writeValueAsString(allRecs);

        when(serviceLayer.createAlbumRec(inputAlbumRec1)).thenReturn(outputAlbumRec1);
        when(serviceLayer.findAllAlbumRecs()).thenReturn(allRecs);
        when(serviceLayer.findAlbumRecById(2L)).thenReturn(outputAlbumRec2);
    }

    @Test
    public void shouldAddAlbumRecOnPostRequest() throws Exception {
        mockMvc.perform(post("/recommendations/album")
                        .content(inputRecString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputRecString1));
    }

    @Test
    public void shouldGetArrayOfAlbumRecommendations() throws Exception {
        mockMvc.perform(get("/recommendations/album"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allRecsString));
    }

    @Test
    public void shouldGetAlbumRecById() throws Exception {
        mockMvc.perform(get("/recommendations/album/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputRecString2));
    }

    @Test
    public void shouldUpdateAlbumRec() throws Exception {
        mockMvc.perform(put("/recommendations/album/1")
                        .content(editedRecString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteAlbumRec() throws Exception {
        mockMvc.perform(delete("/recommendations/album/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRespondWithBadRequestWhenAlbumRecCreateRequestIsBad() throws Exception {
        AlbumRecommendation rec1 = new AlbumRecommendation();
        rec1.setLiked(true);
        String inputRec1 = mapper.writeValueAsString(rec1);


        mockMvc.perform(post("/recommendations/album")
                        .content(inputRec1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());       // Assert HttpStatus 400 Response

    }

    @Test
    public void shouldReturn404WhenFindingInvalidRecommendationId() throws Exception {
        mockMvc.perform(get("/recommendations/album/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn422WhenPutRequestContainsInvalidIds() throws Exception {
        mockMvc.perform(put("/recommendations/album/999")
                        .content(editedRecString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}