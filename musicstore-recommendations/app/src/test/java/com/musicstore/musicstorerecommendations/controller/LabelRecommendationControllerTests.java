package com.musicstore.musicstorerecommendations.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicstore.musicstorerecommendations.model.LabelRecommendation;
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
@WebMvcTest(LabelRecommendationController.class)
public class LabelRecommendationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer serviceLayer;

    private ObjectMapper mapper = new ObjectMapper();

    LabelRecommendation inputLabelRec1;
    LabelRecommendation inputLabelRec2;
    LabelRecommendation editedInputRec1;
    LabelRecommendation outputLabelRec1;
    LabelRecommendation outputLabelRec2;
    List<LabelRecommendation> allRecs;

    String inputRecString1;
    String inputRecString2;
    String editedRecString1;
    String outputRecString1;
    String outputRecString2;
    String allRecsString;

    @Before
    public void setUp() throws Exception {
        inputLabelRec1 = new LabelRecommendation(1L, 1L, true);
        inputLabelRec2 = new LabelRecommendation(2L, 1L, true);
        outputLabelRec1 = new LabelRecommendation(1L,1L, 1L, true);
        outputLabelRec2 = new LabelRecommendation(2L,2L, 1L, true);
        editedInputRec1 = outputLabelRec1;
        editedInputRec1.setUserId(2L);
        editedInputRec1.setLiked(false);

        inputRecString1 = mapper.writeValueAsString(inputLabelRec1);
        inputRecString2 = mapper.writeValueAsString(inputLabelRec2);
        outputRecString1 = mapper.writeValueAsString(outputLabelRec1);
        outputRecString2 = mapper.writeValueAsString(outputLabelRec2);
        editedRecString1 = mapper.writeValueAsString(editedInputRec1);
        allRecs = Arrays.asList(new LabelRecommendation[]{outputLabelRec1, outputLabelRec2});
        allRecsString = mapper.writeValueAsString(allRecs);

        when(serviceLayer.createLabelRec(inputLabelRec1)).thenReturn(outputLabelRec1);
        when(serviceLayer.findAllLabelRecs()).thenReturn(allRecs);
        when(serviceLayer.findLabelRecById(2L)).thenReturn(outputLabelRec2);
    }

    @Test
    public void shouldAddLabelRecOnPostRequest() throws Exception {
        mockMvc.perform(post("/recommendations/label")
                        .content(inputRecString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputRecString1));
    }

    @Test
    public void shouldGetArrayOfLabelRecommendations() throws Exception {
        mockMvc.perform(get("/recommendations/label"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allRecsString));
    }

    @Test
    public void shouldGetLabelRecById() throws Exception {
        mockMvc.perform(get("/recommendations/label/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputRecString2));
    }

    @Test
    public void shouldUpdateLabelRec() throws Exception {
        mockMvc.perform(put("/recommendations/label/1")
                        .content(editedRecString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteLabelRec() throws Exception {
        mockMvc.perform(delete("/recommendations/label/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRespondWithBadRequestWhenLabelRecCreateRequestIsBad() throws Exception {
        LabelRecommendation rec1 = new LabelRecommendation();
        rec1.setLiked(true);
        String inputRec1 = mapper.writeValueAsString(rec1);


        mockMvc.perform(post("/recommendations/label")
                        .content(inputRec1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());       // Assert HttpStatus 400 Response

    }

    @Test
    public void shouldReturn404WhenFindingInvalidRecommendationId() throws Exception {
        mockMvc.perform(get("/recommendations/label/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn422WhenPutRequestContainsInvalidIds() throws Exception {
        mockMvc.perform(put("/recommendations/label/999")
                        .content(editedRecString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}