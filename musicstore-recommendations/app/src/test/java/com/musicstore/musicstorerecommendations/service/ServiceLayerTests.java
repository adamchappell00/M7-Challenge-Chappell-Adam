package com.musicstore.musicstorerecommendations.service;

import com.musicstore.musicstorerecommendations.model.AlbumRecommendation;
import com.musicstore.musicstorerecommendations.model.ArtistRecommendation;
import com.musicstore.musicstorerecommendations.model.LabelRecommendation;
import com.musicstore.musicstorerecommendations.model.TrackRecommendation;
import com.musicstore.musicstorerecommendations.repository.AlbumRecommendationRepository;
import com.musicstore.musicstorerecommendations.repository.ArtistRecommendationRepository;
import com.musicstore.musicstorerecommendations.repository.LabelRecommendationRepository;
import com.musicstore.musicstorerecommendations.repository.TrackRecommendationRepository;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class ServiceLayerTests {

    ServiceLayer service;

    ArtistRecommendationRepository artistRecRepo;
    AlbumRecommendationRepository albumRecRepo;
    LabelRecommendationRepository labelRecRepo;
    TrackRecommendationRepository trackRecRepo;

    @Before
    public void setUp() throws Exception {
        setUpArtistRecRepositoryMock();
        setUpAlbumRecRepositoryMock();
        setUpLabelRecRepositoryMock();
        setUpTrackRecRepositoryMock();

        service = new ServiceLayer(albumRecRepo, labelRecRepo, trackRecRepo, artistRecRepo);
    }
    private void setUpArtistRecRepositoryMock() {
        artistRecRepo = mock(ArtistRecommendationRepository.class);
        ArtistRecommendation artistRec1 = new ArtistRecommendation(1L,1L,true);
        ArtistRecommendation artistRec2 = new ArtistRecommendation(2L,1L,true);
        ArtistRecommendation artistRec1Saved = new ArtistRecommendation(1L,1L,1L,true);
        ArtistRecommendation artistRec2Saved = new ArtistRecommendation(2L,2L,1L, true);
        List<ArtistRecommendation> artistRecList = new ArrayList<>();
        artistRecList.add(artistRec1Saved);
        artistRecList.add(artistRec2Saved);

        doReturn(artistRec1Saved).when(artistRecRepo).save(artistRec1);
        doReturn(Optional.of(artistRec1Saved)).when(artistRecRepo).findById(1L);
        doReturn(artistRecList).when(artistRecRepo).findAll();
        Optional<ArtistRecommendation> nullRec = Optional.ofNullable(null);
        doReturn(nullRec).when(artistRecRepo).findById(3752L);
    }
    private void setUpAlbumRecRepositoryMock() {
        albumRecRepo = mock(AlbumRecommendationRepository.class);
        AlbumRecommendation albumRec1 = new AlbumRecommendation(1L,1L,true);
        AlbumRecommendation albumRec2 = new AlbumRecommendation(2L,1L,true);
        AlbumRecommendation albumRec1Saved = new AlbumRecommendation(1L,1L,1L,true);
        AlbumRecommendation albumRec2Saved = new AlbumRecommendation(2L,2L,1L, true);
        List<AlbumRecommendation> albumRecList = new ArrayList<>();
        albumRecList.add(albumRec1Saved);
        albumRecList.add(albumRec2Saved);

        doReturn(albumRec1Saved).when(albumRecRepo).save(albumRec1);
        doReturn(Optional.of(albumRec1Saved)).when(albumRecRepo).findById(1L);
        doReturn(albumRecList).when(albumRecRepo).findAll();
        Optional<AlbumRecommendation> nullRec = Optional.ofNullable(null);
        doReturn(nullRec).when(albumRecRepo).findById(3752L);
    }
    private void setUpLabelRecRepositoryMock() {
        labelRecRepo = mock(LabelRecommendationRepository.class);
        LabelRecommendation labelRec1 = new LabelRecommendation(1L,1L,true);
        LabelRecommendation labelRec2 = new LabelRecommendation(2L,1L,true);
        LabelRecommendation labelRec1Saved = new LabelRecommendation(1L,1L,1L,true);
        LabelRecommendation labelRec2Saved = new LabelRecommendation(2L,2L,1L, true);
        List<LabelRecommendation> labelRecList = new ArrayList<>();
        labelRecList.add(labelRec1Saved);
        labelRecList.add(labelRec2Saved);

        doReturn(labelRec1Saved).when(labelRecRepo).save(labelRec1);
        doReturn(Optional.of(labelRec1Saved)).when(labelRecRepo).findById(1L);
        doReturn(labelRecList).when(labelRecRepo).findAll();
        Optional<LabelRecommendation> nullRec = Optional.ofNullable(null);
        doReturn(nullRec).when(labelRecRepo).findById(3752L);
    }
    private void setUpTrackRecRepositoryMock() {
        trackRecRepo = mock(TrackRecommendationRepository.class);
        TrackRecommendation trackRec1 = new TrackRecommendation(1L,1L,true);
        TrackRecommendation trackRec2 = new TrackRecommendation(2L,1L,true);
        TrackRecommendation trackRec1Saved = new TrackRecommendation(1L,1L,1L,true);
        TrackRecommendation trackRec2Saved = new TrackRecommendation(2L,2L,1L, true);
        List<TrackRecommendation> trackRecList = new ArrayList<>();
        trackRecList.add(trackRec1Saved);
        trackRecList.add(trackRec2Saved);

        doReturn(trackRec1Saved).when(trackRecRepo).save(trackRec1);
        doReturn(Optional.of(trackRec1Saved)).when(trackRecRepo).findById(1L);
        doReturn(trackRecList).when(trackRecRepo).findAll();
        Optional<TrackRecommendation> nullRec = Optional.ofNullable(null);
        doReturn(nullRec).when(trackRecRepo).findById(3752L);
    }
    // Album Recommendation CRUD Section
    @Test
    public void shouldSaveAlbumRecommendation() {
        AlbumRecommendation albumRecInput = new AlbumRecommendation(1L,1L,true);
        AlbumRecommendation albumRecOutput = new AlbumRecommendation(1L,1L,1L,true);
        AlbumRecommendation actualResult = service.createAlbumRec(albumRecInput);

        assertEquals(albumRecOutput, actualResult);
    }

    @Test
    public void shouldFindAllAlbumRecommendations() {
        List<AlbumRecommendation> fromService = service.findAllAlbumRecs();
        AlbumRecommendation albumRecOutput = new AlbumRecommendation(1L,1L,1L,true);
        assertEquals(2, fromService.size());
    }

    @Test
    public void shouldFindAlbumRecById() {
        AlbumRecommendation expectedAlbumRecOutput = new AlbumRecommendation(1L,1L,1L,true);
        AlbumRecommendation actualResult = service.findAlbumRecById(1L);

        assertEquals(expectedAlbumRecOutput, actualResult);
    }

    @Test
    public void shouldUpdateAlbumRec() {
        AlbumRecommendation expectedAlbumRec = new AlbumRecommendation(1L, 3L,3L, true);
        service.updateAlbumRec(new AlbumRecommendation(1L, 3L,3L, true));

        verify(albumRecRepo).save(expectedAlbumRec);
    }

    @Test
    public void shouldDeleteAlbumRec() {
        service.deleteAlbumRec(1L);
        verify(albumRecRepo).deleteById(1L);
    }
    // Artist Recommendation CRUD Section
    @Test
    public void shouldSaveArtistRecommendation() {
        ArtistRecommendation artistRecInput = new ArtistRecommendation(1L,1L,true);
        ArtistRecommendation artistRecOutput = new ArtistRecommendation(1L,1L,1L,true);
        ArtistRecommendation actualResult = service.createArtistRec(artistRecInput);

        assertEquals(artistRecOutput, actualResult);
    }

    @Test
    public void shouldFindAllArtistRecommendations() {
        List<ArtistRecommendation> fromService = service.findAllArtistRecs();
        ArtistRecommendation artistRecOutput = new ArtistRecommendation(1L,1L,1L,true);
        assertEquals(2, fromService.size());
    }

    @Test
    public void shouldFindArtistRecById() {
        ArtistRecommendation expectedArtistRecOutput = new ArtistRecommendation(1L,1L,1L,true);
        ArtistRecommendation actualResult = service.findArtistRecById(1L);

        assertEquals(expectedArtistRecOutput, actualResult);
    }

    @Test
    public void shouldUpdateArtistRec() {
        ArtistRecommendation expectedArtistRec = new ArtistRecommendation(1L, 3L,3L, true);
        service.updateArtistRec(new ArtistRecommendation(1L, 3L,3L, true));

        verify(artistRecRepo).save(expectedArtistRec);
    }

    @Test
    public void shouldDeleteArtistRec() {
        service.deleteArtistRec(1L);
        verify(artistRecRepo).deleteById(1L);
    }

    // Label Recommendation CRUD Section
    @Test
    public void shouldSaveLabelRecommendation() {
        LabelRecommendation labelRecInput = new LabelRecommendation(1L,1L,true);
        LabelRecommendation labelRecOutput = new LabelRecommendation(1L,1L,1L,true);
        LabelRecommendation actualResult = service.createLabelRec(labelRecInput);

        assertEquals(labelRecOutput, actualResult);
    }

    @Test
    public void shouldFindAllLabelRecommendations() {
        List<LabelRecommendation> fromService = service.findAllLabelRecs();
        LabelRecommendation labelRecOutput = new LabelRecommendation(1L,1L,1L,true);
        assertEquals(2, fromService.size());
    }

    @Test
    public void shouldFindLabelRecById() {
        LabelRecommendation expectedLabelRecOutput = new LabelRecommendation(1L,1L,1L,true);
        LabelRecommendation actualResult = service.findLabelRecById(1L);

        assertEquals(expectedLabelRecOutput, actualResult);
    }

    @Test
    public void shouldUpdateLabelRec() {
        LabelRecommendation expectedLabelRec = new LabelRecommendation(1L, 3L,3L, true);
        service.updateLabelRec(new LabelRecommendation(1L, 3L,3L, true));

        verify(labelRecRepo).save(expectedLabelRec);
    }

    @Test
    public void shouldDeleteLabelRec() {
        service.deleteLabelRec(1L);
        verify(labelRecRepo).deleteById(1L);
    }
    // Track Recommendation CRUD Section
    @Test
    public void shouldSaveTrackRecommendation() {
        TrackRecommendation labelRecInput = new TrackRecommendation(1L,1L,true);
        TrackRecommendation labelRecOutput = new TrackRecommendation(1L,1L,1L,true);
        TrackRecommendation actualResult = service.createTrackRec(labelRecInput);

        assertEquals(labelRecOutput, actualResult);
    }

    @Test
    public void shouldFindAllTrackRecommendations() {
        List<TrackRecommendation> fromService = service.findAllTrackRecs();
        TrackRecommendation labelRecOutput = new TrackRecommendation(1L,1L,1L,true);
        assertEquals(2, fromService.size());
    }

    @Test
    public void shouldFindTrackRecById() {
        TrackRecommendation expectedTrackRecOutput = new TrackRecommendation(1L,1L,1L,true);
        TrackRecommendation actualResult = service.findTrackRecById(1L);

        assertEquals(expectedTrackRecOutput, actualResult);
    }

    @Test
    public void shouldUpdateTrackRec() {
        TrackRecommendation expectedTrackRec = new TrackRecommendation(1L, 3L,3L, true);
        service.updateTrackRec(new TrackRecommendation(1L, 3L,3L, true));

        verify(trackRecRepo).save(expectedTrackRec);
    }

    @Test
    public void shouldDeleteTrackRec() {
        service.deleteTrackRec(1L);
        verify(trackRecRepo).deleteById(1L);
    }
}