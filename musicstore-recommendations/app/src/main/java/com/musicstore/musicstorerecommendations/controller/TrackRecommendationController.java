package com.musicstore.musicstorerecommendations.controller;


import com.musicstore.musicstorerecommendations.exception.EntityNotFoundException;
import com.musicstore.musicstorerecommendations.exception.UnprocessableRequestException;
import com.musicstore.musicstorerecommendations.model.TrackRecommendation;
import com.musicstore.musicstorerecommendations.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/recommendations/track")
public class TrackRecommendationController {

    @Autowired
    private ServiceLayer serviceLayer;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<TrackRecommendation> getListOfAllTrackRecs() {
        return serviceLayer.findAllTrackRecs();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public TrackRecommendation findTrackRecommendationById(@PathVariable Long id) {
        TrackRecommendation returnTrackRec = serviceLayer.findTrackRecById(id);

        if (returnTrackRec == null) {
            throw new EntityNotFoundException("No Track Recommendation with Id " + id + " was found");
        }
        return returnTrackRec;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public TrackRecommendation createTrackRecommendation(@Valid @RequestBody TrackRecommendation trackRec) {
        return serviceLayer.createTrackRec(trackRec);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrackRecommendation(@PathVariable Long id) {
        serviceLayer.deleteTrackRec(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateTrackRecommendation(@PathVariable Long id, @RequestBody TrackRecommendation updatedTrackRec) {
        if (updatedTrackRec.getId() == null) {
            updatedTrackRec.setId(id);
        }
        if (!updatedTrackRec.getId().equals(id)) {
            throw new UnprocessableRequestException("Track Recommendation Id in request must match URL path id");
        }
        serviceLayer.updateTrackRec(updatedTrackRec);
    }
}