package com.musicstore.musicstorecatalog.controller;

import com.musicstore.musicstorecatalog.exception.EntityNotFoundException;
import com.musicstore.musicstorecatalog.exception.UnprocessableRequestException;
import com.musicstore.musicstorecatalog.model.Album;
import com.musicstore.musicstorecatalog.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {

    @Autowired
    private ServiceLayer serviceLayer;

    @RequestMapping(method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Album> getAllAlbums() {
            return serviceLayer.findAllAlbums();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Album findAlbumById(@PathVariable Long id) {
        Album returnAlbum = serviceLayer.findAlbumById(id);

        if(returnAlbum == null){
            throw new EntityNotFoundException("No Album with Id " + id + " was found");
        }
        return returnAlbum;
    }

    @RequestMapping( method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Album createAlbum(@Valid @RequestBody Album album) {
        return serviceLayer.createAlbum(album);
    }

    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable Long id) {
        serviceLayer.deleteAlbum(id);
    }

    @RequestMapping(value="{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateAlbum(@PathVariable Long id, @RequestBody Album updatedAlbum) {
        if (updatedAlbum.getId() == null) {
            updatedAlbum.setId(id);
        }
        if (!updatedAlbum.getId().equals(id)) {
            throw new UnprocessableRequestException("Album Id in request must match URL path id");

        }
        serviceLayer.updateAlbum(updatedAlbum);
    }

}