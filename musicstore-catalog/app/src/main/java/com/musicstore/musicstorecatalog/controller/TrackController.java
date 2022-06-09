package com.musicstore.musicstorecatalog.controller;

import com.musicstore.musicstorecatalog.exception.EntityNotFoundException;
import com.musicstore.musicstorecatalog.exception.UnprocessableRequestException;
import com.musicstore.musicstorecatalog.model.Label;
import com.musicstore.musicstorecatalog.model.Track;
import com.musicstore.musicstorecatalog.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/track")
public class TrackController {

    @Autowired
    private ServiceLayer serviceLayer;

    @RequestMapping(method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Track> getAllTracks() {
        return serviceLayer.findAllTracks();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Track findTrackById(@PathVariable Long id) {
        Track returnTrack = serviceLayer.findTrackById(id);
        if(returnTrack == null){
            throw new EntityNotFoundException("No Track with Id " + id + " was found");
        }
        return returnTrack;
    }

    @RequestMapping( method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Track createTrack(@Valid @RequestBody Track track) {
        return serviceLayer.createTrack(track);
    }

    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrack(@PathVariable Long id) {
        serviceLayer.deleteTrack(id);
    }

    @RequestMapping(value="{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateTrack(@PathVariable Long id, @RequestBody Track updatedTrack) {
        if (updatedTrack.getId() == null) {
            updatedTrack.setId(id);
        }
        if (!updatedTrack.getId().equals(id)) {
            throw new UnprocessableRequestException("Track Id in request must match URL path id");
        }
        serviceLayer.updateTrack(updatedTrack);
    }
}