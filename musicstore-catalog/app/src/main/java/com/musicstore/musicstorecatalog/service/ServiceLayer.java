package com.musicstore.musicstorecatalog.service;

import com.musicstore.musicstorecatalog.exception.EntityNotFoundException;
import com.musicstore.musicstorecatalog.model.Album;
import com.musicstore.musicstorecatalog.model.Artist;
import com.musicstore.musicstorecatalog.model.Label;
import com.musicstore.musicstorecatalog.model.Track;
import com.musicstore.musicstorecatalog.repository.AlbumRepository;
import com.musicstore.musicstorecatalog.repository.ArtistRepository;
import com.musicstore.musicstorecatalog.repository.LabelRepository;
import com.musicstore.musicstorecatalog.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ServiceLayer {

   
    private final AlbumRepository albumRepository;
    private final LabelRepository labelRepository;
    private final TrackRepository trackRepository;
    private final ArtistRepository artistRepository;

    @Autowired
    public ServiceLayer(AlbumRepository albumRepository, LabelRepository labelRepository, TrackRepository trackRepository, ArtistRepository artistRepository){
        this.albumRepository = albumRepository;
        this.labelRepository = labelRepository;
        this.trackRepository = trackRepository;
        this.artistRepository = artistRepository;
    }

    // ALBUM CRUD SECTION
    public List<Album> findAllAlbums() {
        return albumRepository.findAll();
    }

    public Album findAlbumById(Long id) {
        Optional<Album> album = albumRepository.findById(id);
        return album.isPresent() ? album.get() : null;
    }

    public Album createAlbum(Album album) {
        return albumRepository.save(album);
    }

    public void updateAlbum(Album updatedAlbum) {
        Optional<Album> opt = albumRepository.findById(updatedAlbum.getId());
        if(opt.isPresent()){
            albumRepository.save(updatedAlbum);
        }else {
            throw new EntityNotFoundException("Cannot Update Album, none found for Id: " + updatedAlbum.getId());
        }
    }
    public void deleteAlbum(Long id) {
        Optional<Album> opt = albumRepository.findById(id);
        if(opt.isPresent()){
            albumRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Cannot Delete Album, none found for Id: " + id);
        }
    }
    // LABEL CRUD SECTION
    public List<Label> findAllLabels() {
        return labelRepository.findAll();
    }

    public Label findLabelById(Long id) {
        Optional<Label> label = labelRepository.findById(id);
        return label.isPresent() ? label.get() : null;
    }

    public Label createLabel(Label label) {
        return labelRepository.save(label);
    }

    public void updateLabel(Label updatedLabel) {
        Optional<Label> opt = labelRepository.findById(updatedLabel.getId());
        if(opt.isPresent()){
            labelRepository.save(updatedLabel);
        }else {
            throw new EntityNotFoundException("Cannot Update Label, none found for Id: " + updatedLabel.getId());
        }
    }
    public void deleteLabel(Long id) {
        Optional<Label> opt = labelRepository.findById(id);
        if(opt.isPresent()){
            labelRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Cannot Delete Label, none found for Id: " + id);
        }
    }


    // ARTIST CRUD SECTION
    public List<Artist> findAllArtists() {
        return artistRepository.findAll();
    }

    public Artist findArtistById(Long id) {
        Optional<Artist> artist = artistRepository.findById(id);
        return artist.isPresent() ? artist.get() : null;
    }

    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public void updateArtist(Artist updatedArtist) {
        Optional<Artist> opt = artistRepository.findById(updatedArtist.getId());
        if(opt.isPresent()){
            artistRepository.save(updatedArtist);
        }else {
            throw new EntityNotFoundException("Cannot Update Artist, none found for Id: " + updatedArtist.getId());
        }
    }
    public void deleteArtist(Long id) {
        Optional<Artist> opt = artistRepository.findById(id);
        if(opt.isPresent()){
            artistRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Cannot Delete Artist, none found for Id: " + id);
        }
    }


    // TRACK CRUD SECTION
    public List<Track> findAllTracks() {
        return trackRepository.findAll();
    }

    public Track findTrackById(Long id) {
        Optional<Track> track = trackRepository.findById(id);
        return track.isPresent() ? track.get() : null;
    }

    public Track createTrack(Track track) {
        return trackRepository.save(track);
    }

    public void updateTrack(Track updatedTrack) {
        Optional<Track> opt = trackRepository.findById(updatedTrack.getId());
        if(opt.isPresent()){
            trackRepository.save(updatedTrack);
        }else {
            throw new EntityNotFoundException("Cannot Update Track, none found for Id: " + updatedTrack.getId());
        }
    }
    public void deleteTrack(Long id) {
        Optional<Track> opt = trackRepository.findById(id);
        if(opt.isPresent()){
           trackRepository.deleteById(id);
        }else {
            throw new EntityNotFoundException("Cannot Delete Track, none found for Id: " + id);
        }
    }
}