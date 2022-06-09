package com.musicstore.musicstorerecommendations.service;

import com.musicstore.musicstorerecommendations.exception.EntityNotFoundException;
import com.musicstore.musicstorerecommendations.model.AlbumRecommendation;
import com.musicstore.musicstorerecommendations.model.ArtistRecommendation;
import com.musicstore.musicstorerecommendations.model.LabelRecommendation;
import com.musicstore.musicstorerecommendations.model.TrackRecommendation;
import com.musicstore.musicstorerecommendations.repository.AlbumRecommendationRepository;
import com.musicstore.musicstorerecommendations.repository.ArtistRecommendationRepository;
import com.musicstore.musicstorerecommendations.repository.LabelRecommendationRepository;
import com.musicstore.musicstorerecommendations.repository.TrackRecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ServiceLayer {


    private final AlbumRecommendationRepository albumRecRepository;
    private final LabelRecommendationRepository labelRecRepository;
    private final TrackRecommendationRepository trackRecRepository;
    private final ArtistRecommendationRepository artistRecRepository;

    @Autowired
    public ServiceLayer(AlbumRecommendationRepository albumRecRepo, LabelRecommendationRepository labelRecRepo, TrackRecommendationRepository trackRecRepo, ArtistRecommendationRepository artistRecRepo){
        this.albumRecRepository = albumRecRepo;
        this.labelRecRepository = labelRecRepo;
        this.trackRecRepository = trackRecRepo;
        this.artistRecRepository = artistRecRepo;
    }

    // Album Recommendation CRUD Section
    public List<AlbumRecommendation> findAllAlbumRecs() {
        return albumRecRepository.findAll();
    }

    public AlbumRecommendation findAlbumRecById(Long id) {
        Optional<AlbumRecommendation> albumRec = albumRecRepository.findById(id);
        return albumRec.isPresent() ? albumRec.get() : null;
    }

    public AlbumRecommendation createAlbumRec(AlbumRecommendation albumRec) {
        return albumRecRepository.save(albumRec);
    }

    public void updateAlbumRec(AlbumRecommendation updatedAlbumRec) {
        Optional<AlbumRecommendation> opt = albumRecRepository.findById(updatedAlbumRec.getId());
        if(opt.isPresent()){
            albumRecRepository.save(updatedAlbumRec);
        }else {
            throw new EntityNotFoundException("Cannot Update Album Recommendation, none found for Id: " + updatedAlbumRec.getId());
        }
    }
    public void deleteAlbumRec(Long id) {
        Optional<AlbumRecommendation> opt = albumRecRepository.findById(id);
        if(opt.isPresent()){
            albumRecRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Cannot Delete Album Recommendation, none found for Id: " + id);
        }
    }
    // Label Recommendation CRUD Section
    public List<LabelRecommendation> findAllLabelRecs() {
        return labelRecRepository.findAll();
    }

    public LabelRecommendation findLabelRecById(Long id) {
        Optional<LabelRecommendation> labelRec = labelRecRepository.findById(id);
        return labelRec.isPresent() ? labelRec.get() : null;
    }

    public LabelRecommendation createLabelRec(LabelRecommendation labelRec) {
        return labelRecRepository.save(labelRec);
    }

    public void updateLabelRec(LabelRecommendation updatedLabelRec) {
        Optional<LabelRecommendation> opt = labelRecRepository.findById(updatedLabelRec.getId());
        if(opt.isPresent()){
           labelRecRepository.save(updatedLabelRec);
        }else {
            throw new EntityNotFoundException("Cannot Update Label Recommendation, none found for Id: " + updatedLabelRec.getId());
        }
    }
    public void deleteLabelRec(Long id) {
        Optional<LabelRecommendation> opt = labelRecRepository.findById(id);
        if(opt.isPresent()){
            labelRecRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Cannot Delete Label Recommendation, none found for Id: " + id);
        }
    }
    // Track Recommendation CRUD Section
    public List<TrackRecommendation> findAllTrackRecs() {
        return trackRecRepository.findAll();
    }

    public TrackRecommendation findTrackRecById(Long id) {
        Optional<TrackRecommendation> trackRec = trackRecRepository.findById(id);
        return trackRec.isPresent() ? trackRec.get() : null;
    }

    public TrackRecommendation createTrackRec(TrackRecommendation trackRec) {
        return trackRecRepository.save(trackRec);
    }

    public void updateTrackRec(TrackRecommendation updatedTrackRec) {
        Optional<TrackRecommendation> opt = trackRecRepository.findById(updatedTrackRec.getId());
        if(opt.isPresent()){
            trackRecRepository.save(updatedTrackRec);
        }else {
            throw new EntityNotFoundException("Cannot Update Track Recommendation, none found for Id: " + updatedTrackRec.getId());
        }
    }
    public void deleteTrackRec(Long id) {
        Optional<TrackRecommendation> opt = trackRecRepository.findById(id);
        if(opt.isPresent()){
            trackRecRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Cannot Delete Track Recommendation, none found for Id: " + id);
        }
    }
    // Artist Recommendation CRUD Section
    public List<ArtistRecommendation> findAllArtistRecs() {
        return artistRecRepository.findAll();
    }

    public ArtistRecommendation findArtistRecById(Long id) {
        Optional<ArtistRecommendation> artistRec = artistRecRepository.findById(id);
        return artistRec.isPresent() ? artistRec.get() : null;
    }

    public ArtistRecommendation createArtistRec(ArtistRecommendation artistRec) {
        return artistRecRepository.save(artistRec);
    }

    public void updateArtistRec(ArtistRecommendation updatedArtistRec) {
        Optional<ArtistRecommendation> opt = artistRecRepository.findById(updatedArtistRec.getId());
        if(opt.isPresent()){
            artistRecRepository.save(updatedArtistRec);
        }else {
            throw new EntityNotFoundException("Cannot Update Artist Recommendation, none found for Id: " + updatedArtistRec.getId());
        }
    }
    public void deleteArtistRec(Long id) {
        Optional<ArtistRecommendation> opt = artistRecRepository.findById(id);
        if(opt.isPresent()){
            artistRecRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Cannot Delete Artist Recommendation, none found for Id: " + id);
        }
    }
}