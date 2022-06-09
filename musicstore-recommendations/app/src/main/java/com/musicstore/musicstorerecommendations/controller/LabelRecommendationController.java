package com.musicstore.musicstorerecommendations.controller;

import com.musicstore.musicstorerecommendations.exception.EntityNotFoundException;
import com.musicstore.musicstorerecommendations.exception.UnprocessableRequestException;
import com.musicstore.musicstorerecommendations.model.LabelRecommendation;
import com.musicstore.musicstorerecommendations.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/recommendations/label")
public class LabelRecommendationController {

    @Autowired
    private ServiceLayer serviceLayer;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<LabelRecommendation> getListOfAllLabelRecs() {
        return serviceLayer.findAllLabelRecs();

    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public LabelRecommendation findLabelRecommendationById(@PathVariable Long id) {
        LabelRecommendation returnLabelRec = serviceLayer.findLabelRecById(id);

        if (returnLabelRec == null) {
            throw new EntityNotFoundException("No Label Recommendation with Id " + id + " was found");
        }
        return returnLabelRec;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public LabelRecommendation createLabelRecommendation(@Valid @RequestBody LabelRecommendation labelRec) {
        return serviceLayer.createLabelRec(labelRec);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabelRecommendation(@PathVariable Long id) {
        serviceLayer.deleteLabelRec(id);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateLabelRecommendation(@PathVariable Long id, @RequestBody LabelRecommendation updatedLabelRec) {
        if (updatedLabelRec.getId() == null) {
            updatedLabelRec.setId(id);
        }
        if (!updatedLabelRec.getId().equals(id)) {
            throw new UnprocessableRequestException("Label Recommendation Id in request must match URL path id");

        }
        serviceLayer.updateLabelRec(updatedLabelRec);
    }


}