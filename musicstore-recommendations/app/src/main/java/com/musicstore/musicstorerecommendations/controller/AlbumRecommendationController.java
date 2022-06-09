package com.musicstore.musicstorerecommendations.controller;

import com.musicstore.musicstorerecommendations.exception.EntityNotFoundException;
import com.musicstore.musicstorerecommendations.exception.UnprocessableRequestException;
import com.musicstore.musicstorerecommendations.model.AlbumRecommendation;
import com.musicstore.musicstorerecommendations.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/recommendations/album")
public class AlbumRecommendationController {

    @Autowired
    private ServiceLayer serviceLayer;

    @RequestMapping(method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<AlbumRecommendation> getListOfAllAlbumRecs() {
        return serviceLayer.findAllAlbumRecs();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public AlbumRecommendation findAlbumRecommendationById(@PathVariable Long id) {
        AlbumRecommendation returnAlbumRec = serviceLayer.findAlbumRecById(id);

        if(returnAlbumRec == null){
            throw new EntityNotFoundException("No Album Recommendation with Id " + id + " was found");
        }
        return returnAlbumRec;
    }

    @RequestMapping( method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public AlbumRecommendation createAlbumRecommendation(@Valid @RequestBody AlbumRecommendation albumRec) {
        return serviceLayer.createAlbumRec(albumRec);
    }

    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbumRecommendation(@PathVariable Long id) {
        serviceLayer.deleteAlbumRec(id);
    }

    @RequestMapping(value="{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateAlbumRecommendation(@PathVariable Long id, @RequestBody AlbumRecommendation updatedAlbumRec) {
        if (updatedAlbumRec.getId() == null) {
            updatedAlbumRec.setId(id);
        }
        if (!updatedAlbumRec.getId().equals(id)) {
            throw new UnprocessableRequestException("Album  Recommendation Id in request must match URL path id");

        }
        serviceLayer.updateAlbumRec(updatedAlbumRec);
    }
}