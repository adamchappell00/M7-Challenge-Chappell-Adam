package com.musicstore.musicstorerecommendations.controller;


import com.musicstore.musicstorerecommendations.exception.EntityNotFoundException;
import com.musicstore.musicstorerecommendations.exception.UnprocessableRequestException;
import com.musicstore.musicstorerecommendations.model.ArtistRecommendation;
import com.musicstore.musicstorerecommendations.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/recommendations/artist")
public class ArtistRecommendationController {

    @Autowired
    private ServiceLayer serviceLayer;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistRecommendation> getListOfAllArtistRecs() {
        return serviceLayer.findAllArtistRecs();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public ArtistRecommendation findArtistRecommendationById(@PathVariable Long id) {
        ArtistRecommendation returnArtistRec = serviceLayer.findArtistRecById(id);

        if (returnArtistRec == null) {
            throw new EntityNotFoundException("No Artist Recommendation with Id " + id + " was found");
        }
        return returnArtistRec;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistRecommendation createArtistRecommendation(@Valid @RequestBody ArtistRecommendation artistRec) {
        return serviceLayer.createArtistRec(artistRec);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtistRecommendation(@PathVariable Long id) {
        serviceLayer.deleteArtistRec(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateArtistRecommendation(@PathVariable Long id, @RequestBody ArtistRecommendation updatedArtistRec) {
        if (updatedArtistRec.getId() == null) {
            updatedArtistRec.setId(id);
        }
        if (!updatedArtistRec.getId().equals(id)) {
            throw new UnprocessableRequestException("Artist Recommendation Id in request must match URL path id");
        }
        serviceLayer.updateArtistRec(updatedArtistRec);
    }
}