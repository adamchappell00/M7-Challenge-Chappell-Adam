package com.musicstore.musicstorecatalog.controller;

import com.musicstore.musicstorecatalog.exception.EntityNotFoundException;
import com.musicstore.musicstorecatalog.exception.UnprocessableRequestException;

import com.musicstore.musicstorecatalog.model.Artist;
import com.musicstore.musicstorecatalog.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {

    @Autowired
    private ServiceLayer serviceLayer;

    @RequestMapping(method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Artist> getAllArtists() {
        return serviceLayer.findAllArtists();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Artist findArtistById(@PathVariable Long id) {
        Artist returnArtist = serviceLayer.findArtistById(id);

        if(returnArtist == null){
            throw new EntityNotFoundException("No Artist with Id " + id + " was found");
        }
        return returnArtist;
    }

    @RequestMapping( method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Artist createArtist(@Valid @RequestBody Artist artist) {
        return serviceLayer.createArtist(artist);
    }

    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable Long id) {
        serviceLayer.deleteArtist(id);
    }

    @RequestMapping(value="{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateArtist(@PathVariable Long id, @RequestBody Artist updatedArtist) {
        if (updatedArtist.getId() == null) {
            updatedArtist.setId(id);
        }
        if (!updatedArtist.getId().equals(id)) {
            throw new UnprocessableRequestException("Album Id in request must match URL path id");
        }
        serviceLayer.updateArtist(updatedArtist);
    }
}