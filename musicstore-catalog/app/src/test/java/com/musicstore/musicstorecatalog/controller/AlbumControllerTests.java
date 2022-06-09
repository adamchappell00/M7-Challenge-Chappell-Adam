package com.musicstore.musicstorecatalog.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicstore.musicstorecatalog.model.Album;
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
@WebMvcTest(AlbumController.class)
public class AlbumControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceLayer serviceLayer;

    private ObjectMapper mapper = new ObjectMapper();

    Album inputAlbum1;
    Album inputAlbum2;
    Album editedInput1;
    Album outputAlbum1;
    Album outputAlbum2;
    List<Album> allAlbums;

    String inputString1;
    String inputString2;
    String editedString1;
    String outputString1;
    String outputString2;
    String allAlbumsString;

    @Before
    public void setUp() throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date(1973-03-01);
        String dateString1 = formatter.format(date1);
        Date date2 = new Date(1979-02-04);
        String dateString2 = formatter.format(date2);
        inputAlbum1 = new Album("The Dark Side of the Moon", dateString1, new BigDecimal("9.45"), 1L, 1L);
        inputAlbum2 = new Album("Rumors", dateString2, new BigDecimal("10.98"), 2L, 2L );
        outputAlbum1 = new Album(1L, "The Dark Side of the Moon", dateString1, new BigDecimal("9.45"), 1L, 1L);
        outputAlbum2 = new Album(2L,"Rumors", dateString2, new BigDecimal("10.98"), 2L, 2L );
        editedInput1 = new Album(1L, "Potatoes", dateString1, new BigDecimal("9.45"), 3L, 4L);

        inputString1 = mapper.writeValueAsString(inputAlbum1);
        inputString2 = mapper.writeValueAsString(inputAlbum2);
        outputString1 = mapper.writeValueAsString(outputAlbum1);
        outputString2 = mapper.writeValueAsString(outputAlbum2);
        editedString1 = mapper.writeValueAsString(editedInput1);
        allAlbums = Arrays.asList(new Album[]{outputAlbum1, outputAlbum2});
        allAlbumsString = mapper.writeValueAsString(allAlbums);

        when(serviceLayer.createAlbum(inputAlbum1)).thenReturn(outputAlbum1);
        when(serviceLayer.findAllAlbums()).thenReturn(allAlbums);
        when(serviceLayer.findAlbumById(2L)).thenReturn(outputAlbum2);
    }

    @Test
    public void shouldAddAlbumOnPostRequest() throws Exception {
        mockMvc.perform(post("/album")
                        .content(inputString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().json(outputString1));
    }

    @Test
    public void shouldGetArrayOfAlbums() throws Exception {
        mockMvc.perform(get("/album"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(allAlbumsString));
    }

    @Test
    public void shouldGetAlbumById() throws Exception {
        mockMvc.perform(get("/album/2"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(outputString2));
    }

    @Test
    public void shouldUpdateAlbum() throws Exception {
        mockMvc.perform(put("/album/1")
                        .content(editedString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteAlbum() throws Exception {
        mockMvc.perform(delete("/album/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldRespondWithBadRequestWhenAlbumCreateRequestIsBad() throws Exception {
        Album album1 = new Album();
        album1.setTitle("The New Hit");
        String inputAlbum1 = mapper.writeValueAsString(album1);

        mockMvc.perform(post("/album")
                        .content(inputAlbum1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());       // Assert HttpStatus 400 Response
    }

    @Test
    public void shouldReturn404WhenFindingInvalidAlbumId() throws Exception {
        mockMvc.perform(get("/album/999"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn422WhenPutRequestContainsInvalidIds() throws Exception {
        mockMvc.perform(put("/album/999")
                        .content(editedString1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

}