package com.musicstore.musicstorecatalog.controller;


import com.musicstore.musicstorecatalog.exception.EntityNotFoundException;
import com.musicstore.musicstorecatalog.exception.UnprocessableRequestException;
import com.musicstore.musicstorecatalog.model.Label;
import com.musicstore.musicstorecatalog.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/label")
public class LabelController {

    @Autowired
    private ServiceLayer serviceLayer;

    @RequestMapping(method= RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Label> getAllLabels() {
        return serviceLayer.findAllLabels();
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Label findLabelById(@PathVariable Long id) {
        Label returnLabel = serviceLayer.findLabelById(id);
        if(returnLabel == null){
            throw new EntityNotFoundException("No Label with Id " + id + " was found");
        }
        return returnLabel;
    }

    @RequestMapping( method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Label createLabel(@Valid @RequestBody Label label) {
        return serviceLayer.createLabel(label);
    }

    @RequestMapping(value="{id}", method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable Long id) {
        serviceLayer.deleteLabel(id);
    }

    @RequestMapping(value="{id}", method=RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateLabel(@PathVariable Long id, @RequestBody Label updatedLabel) {
        if (updatedLabel.getId() == null) {
            updatedLabel.setId(id);
        }
        if (!updatedLabel.getId().equals(id)) {
            throw new UnprocessableRequestException("Album Id in request must match URL path id");
        }
        serviceLayer.updateLabel(updatedLabel);
    }
}