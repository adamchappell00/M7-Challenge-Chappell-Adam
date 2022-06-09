package com.musicstore.musicstorecatalog.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicstore.musicstorecatalog.model.Artist;
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
@WebMvcTest(ArtistController.class)
public class ArtistControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer serviceLayer;

    private ObjectMapper mapper = new ObjectMapper();

    Artist inputArtist1;
    Artist inputArtist2;
    Artist editedInput1;
    Artist outputArtist1;
    Artist outputArtist2;
    List<Artist> allArtists;

    String inputString1;
    String inputString2;
    String editedString1;
    String outputString1;
    String outputString2;
    String allArtistsString;

    @Before
    public void setUp() throws Exception{
        inputArtist1 = new Artist("Fleetwood Mac", "@fleetwoodmac","@fleetwoodmac");
        inputArtist2 = new Artist("Pink Floyd", "@darksideofthemoon","@pnkflyd");
        outputArtist1 = new Artist(1L,"Fleetwood Mac", "@fleetwoodmac","@fleetwoodmac");
        outputArtist2 = new Artist(2L,"Pink Floyd", "@darksideofthemoon","@pnkflyd");
        editedInput1 = new Artist(2L, "The Spuds", "@spudnik","@tweety");

        inputString1 = mapper.writeValueAsString(inputArtist1);
        inputString2 = mapper.writeValueAsString(inputArtist2);
        outputString1 = mapper.writeValueAsString(outputArtist1);
        outputString2 = mapper.writeValueAsString(outputArtist2);
        editedString1 = mapper.writeValueAsString(editedInput1);
        allArtists = Arrays.asList(new Artist[]{outputArtist1, outputArtist2});
        allArtistsString = mapper.writeValueAsString(allArtists);

        when(serviceLayer.createArtist(inputArtist1)).thenReturn(outputArtist1);
        when(serviceLayer.findAllArtists()).thenReturn(allArtists);
        when(serviceLayer.findArtistById(2L)).thenReturn(outputArtist2);
    }

    @Test
    public void shouldAddArtistOnPostRequest() throws Exception {
        mockMvc.perform(post("/artist")
                        .content(inputString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputString1));
    }

    @Test
    public void shouldGetArrayOfArtists() throws Exception {
        mockMvc.perform(get("/artist"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allArtistsString));
    }

    @Test
    public void shouldGetArtistById() throws Exception {
        mockMvc.perform(get("/artist/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputString2));
    }

    @Test
    public void shouldUpdateTrack() throws Exception {
        mockMvc.perform(put("/artist/2")
                        .content(editedString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteArtist() throws Exception {
        mockMvc.perform(delete("/artist/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRespondWithBadRequestWhenArtistCreateRequestIsBad() throws Exception {
        Artist artist1 = new Artist();
        artist1.setTwitter("@thoseguys");
        String inputArtist1 = mapper.writeValueAsString(artist1);


        mockMvc.perform(post("/artist")
                        .content(inputArtist1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());       // Assert HttpStatus 400 Response

    }

    @Test
    public void shouldReturn404WhenFindingInvalidArtistId() throws Exception {
        mockMvc.perform(get("/artist/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn422WhenPutRequestContainsInvalidIds() throws Exception {
        mockMvc.perform(put("/artist/999")
                        .content(editedString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}