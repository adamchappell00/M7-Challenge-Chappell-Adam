package com.musicstore.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicstore.musicstorecatalog.model.Track;
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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrackController.class)
public class TrackControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer serviceLayer;

    private ObjectMapper mapper = new ObjectMapper();

    Track inputTrack1;
    Track inputTrack2;
    Track editedInput1;
    Track outputTrack1;
    Track outputTrack2;
    List<Track> allTracks;

    String inputString1;
    String inputString2;
    String editedString1;
    String outputString1;
    String outputString2;
    String allTracksString;

    @Before
    public void setUp() throws Exception{
        inputTrack1 = new Track("I Don't Want to Know", 195, 1L);
        inputTrack2 = new Track("Dreams", 257, 1L);
        outputTrack1 = new Track(1L,"Dreams", 195, 1L);
        outputTrack2 = new Track(2L,"Dreams", 257, 1L);
        editedInput1 = new Track(1L,"Potatoes", 231, 1L);

        inputString1 = mapper.writeValueAsString(inputTrack1);
        inputString2 = mapper.writeValueAsString(inputTrack2);
        outputString1 = mapper.writeValueAsString(outputTrack1);
        outputString2 = mapper.writeValueAsString(outputTrack2);
        editedString1 = mapper.writeValueAsString(editedInput1);
        allTracks = Arrays.asList(new Track[]{outputTrack1, outputTrack2});
        allTracksString = mapper.writeValueAsString(allTracks);

        when(serviceLayer.createTrack(inputTrack1)).thenReturn(outputTrack1);
        when(serviceLayer.findAllTracks()).thenReturn(allTracks);
        when(serviceLayer.findTrackById(2L)).thenReturn(outputTrack2);
    }

    @Test
    public void shouldAddTrackOnPostRequest() throws Exception {
        mockMvc.perform(post("/track")
                        .content(inputString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputString1));
    }

    @Test
    public void shouldGetArrayOfTracks() throws Exception {
        mockMvc.perform(get("/track"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allTracksString));
    }

    @Test
    public void shouldGetTrackById() throws Exception {
        mockMvc.perform(get("/track/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputString2));
    }

    @Test
    public void shouldUpdateTrack() throws Exception {
        mockMvc.perform(put("/track/1")
                        .content(editedString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteTrack() throws Exception {
        mockMvc.perform(delete("/track/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRespondWithBadRequestWhenTrackCreateRequestIsBad() throws Exception {
        Track track1 = new Track();
        track1.setTitle("The New Hit");
        String inputTrack1 = mapper.writeValueAsString(track1);


        mockMvc.perform(post("/track")
                        .content(inputTrack1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());       // Assert HttpStatus 400 Response

    }

    @Test
    public void shouldReturn404WhenFindingInvalidTrackId() throws Exception {
        mockMvc.perform(get("/track/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn422WhenPutRequestContainsInvalidIds() throws Exception {
        mockMvc.perform(put("/track/999")
                        .content(editedString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }


}