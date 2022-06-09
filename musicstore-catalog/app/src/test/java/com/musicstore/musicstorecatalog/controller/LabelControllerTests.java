package com.musicstore.musicstorecatalog.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicstore.musicstorecatalog.model.Label;
import com.musicstore.musicstorecatalog.service.ServiceLayer;
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
@WebMvcTest(LabelController.class)
public class LabelControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer serviceLayer;

    private ObjectMapper mapper = new ObjectMapper();

    Label inputLabel1;
    Label inputLabel2;
    Label editedInput1;
    Label outputLabel1;
    Label outputLabel2;
    List<Label> allLabels;

    String inputString1;
    String inputString2;
    String editedString1;
    String outputString1;
    String outputString2;
    String allLabelsString;

    @Before
    public void setUp() throws Exception{
        inputLabel1 = new Label( "Capitol","capitolrecords.com");
        inputLabel2 = new Label("Warner Bros", "theWB.com");
        outputLabel1 = new Label( 1L,"Capitol","capitolrecords.com");
        outputLabel2 = new Label(2L,"Warner Bros", "theWB.com");
        editedInput1 = new Label(2L,"Big Man Studio ","wbby.com");

        inputString1 = mapper.writeValueAsString(inputLabel1);
        inputString2 = mapper.writeValueAsString(inputLabel2);
        outputString1 = mapper.writeValueAsString(outputLabel1);
        outputString2 = mapper.writeValueAsString(outputLabel2);
        editedString1 = mapper.writeValueAsString(editedInput1);
        allLabels = Arrays.asList(new Label[]{outputLabel1, outputLabel2});
        allLabelsString = mapper.writeValueAsString(allLabels);

        when(serviceLayer.createLabel(inputLabel1)).thenReturn(outputLabel1);
        when(serviceLayer.findAllLabels()).thenReturn(allLabels);
        when(serviceLayer.findLabelById(2L)).thenReturn(outputLabel2);
    }

    @Test
    public void shouldAddLabelOnPostRequest() throws Exception {
        mockMvc.perform(post("/label")
                        .content(inputString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputString1));
    }

    @Test
    public void shouldGetArrayOfLabels() throws Exception {
        mockMvc.perform(get("/label"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allLabelsString));
    }

    @Test
    public void shouldGetLabelById() throws Exception {
        mockMvc.perform(get("/label/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputString2));
    }

    @Test
    public void shouldUpdateLabel() throws Exception {
        mockMvc.perform(put("/label/2")
                        .content(editedString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteLabel() throws Exception {
        mockMvc.perform(delete("/label/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRespondWithBadRequestWhenLabelCreateRequestIsBad() throws Exception {
        Label label1 = new Label();
        label1.setWebsite("thoseguys.com");
        String inputLabel1 = mapper.writeValueAsString(label1);

        mockMvc.perform(post("/label")
                        .content(inputLabel1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());       // Assert HttpStatus 400 Response
    }

    @Test
    public void shouldReturn404WhenFindingInvalidLabelId() throws Exception {
        mockMvc.perform(get("/label/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn422WhenPutRequestContainsInvalidIds() throws Exception {
        mockMvc.perform(put("/label/999")
                        .content(editedString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}