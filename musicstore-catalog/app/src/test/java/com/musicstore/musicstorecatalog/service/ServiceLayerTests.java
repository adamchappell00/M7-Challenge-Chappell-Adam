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
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

public class ServiceLayerTests {

    ServiceLayer service;

    ArtistRepository artistRepo;
    AlbumRepository albumRepo;
    LabelRepository labelRepo;
    TrackRepository trackRepo;

    Album album1;
    Album album1Saved;
    Album album1Edited;
    Album album1EditSave;

    Artist artist1;
    Artist artist1Saved;
    Artist artist1Edited;
    Artist artist1EditSave;

    Label label1;
    Label label1Saved;
    Label label1Edited;
    Label label1EditSave;

    Track track1;
    Track track1Saved;
    Track track1Edited;
    Track track1EditSave;

    @Before
    public void setUp() throws Exception {
        setUpArtistRepositoryMock();
        setUpAlbumRepositoryMock();
        setUpLabelRepositoryMock();
        setUpTrackRepositoryMock();

        service = new ServiceLayer(albumRepo, labelRepo, trackRepo, artistRepo);
    }

    private void setUpArtistRepositoryMock() {
        artistRepo = mock(ArtistRepository.class);
        artist1 = new Artist("Fleetwood Mac", "@fleetwoodmac", "@fleetwoodmac");
        Artist artist2 = new Artist("Pink Floyd", "@darksideofthemoon", "@pnkflyd");
        artist1Saved = new Artist(1L, "Fleetwood Mac", "@fleetwoodmac", "@fleetwoodmac");
        Artist artist2Saved = new Artist(2L, "Pink Floyd", "@darksideofthemoon", "@pnkflyd");
        artist1Edited = new Artist(1L, "Leeroy", "@jenkins", "@fleetwoodmac");
        artist1EditSave = new Artist(1L, "Leeroy", "@jenkins", "@fleetwoodmac");

        List<Artist> artistList = new ArrayList<>();
        artistList.add(artist1Saved);
        artistList.add(artist2Saved);

        doReturn(artist1Saved).when(artistRepo).save(artist1);
        doReturn(artist1EditSave).when(artistRepo).save(artist1Edited);
        doReturn(Optional.of(artist1Saved)).when(artistRepo).findById(1L);
        doReturn(artistList).when(artistRepo).findAll();
        Optional<Artist> nullArtist = Optional.ofNullable(null);
        doReturn(nullArtist).when(artistRepo).findById(3752L);
    }

    private void setUpAlbumRepositoryMock() {
        albumRepo = mock(AlbumRepository.class);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = new Date(1973 - 03 - 01);
        String dateString1 = formatter.format(date1);
        Date date2 = new Date(1979 - 02 - 04);
        String dateString2 = formatter.format(date2);

        album1 = new Album("The Dark Side of the Moon", dateString1, new BigDecimal("9.45"), 1L, 1L);
        Album album2 = new Album("Rumors", dateString2, new BigDecimal("10.98"), 2L, 2L);
        album1Saved = new Album(1L, "The Dark Side of the Moon", dateString1, new BigDecimal("9.45"), 1L, 1L);
        Album album2Saved = new Album(2L, "Rumors", dateString2, new BigDecimal("10.98"), 2L, 2L);
        album1Edited = new Album(1L, "The Definitive Collection", dateString1, new BigDecimal("19.99"), 2L, 1L);
        album1EditSave = new Album(1L, "The Definitive Collection", dateString1, new BigDecimal("19.99"), 2L, 1L);

        List<Album> albumList = new ArrayList<>();
        albumList.add(album1Saved);
        albumList.add(album2Saved);

        doReturn(album1Saved).when(albumRepo).save(album1);
        doReturn(album1EditSave).when(albumRepo).save(album1Edited);
        doReturn(Optional.of(album1Saved)).when(albumRepo).findById(1L);
        doReturn(albumList).when(albumRepo).findAll();
        Optional<Album> nullAlbum = Optional.ofNullable(null);
        doReturn(nullAlbum).when(albumRepo).findById(3752L);
    }

    private void setUpLabelRepositoryMock() {
        labelRepo = mock(LabelRepository.class);
        label1 = new Label("Capitol", "capitolrecords.com");
        Label label2 = new Label("Warner Bros", "theWB.com");
        label1Saved = new Label(1L, "Capitol", "capitolrecords.com");
        Label label2Saved = new Label(2L, "Warner Bros", "theWB.com");
        label1Edited = new Label(1L, "Highway", "hwy.net");
        label1EditSave = new Label(1L, "Highway", "hwy.net");
        List<Label> labelList = new ArrayList<>();
        labelList.add(label1Saved);
        labelList.add(label2Saved);

        doReturn(label1Saved).when(labelRepo).save(label1);
        doReturn(label1EditSave).when(labelRepo).save(label1Edited);
        doReturn(Optional.of(label1Saved)).when(labelRepo).findById(1L);
        doReturn(labelList).when(labelRepo).findAll();
        Optional<Label> nullLabel = Optional.ofNullable(null);
        doReturn(nullLabel).when(labelRepo).findById(3752L);
    }

    private void setUpTrackRepositoryMock() {
        trackRepo = mock(TrackRepository.class);
        track1 = new Track("I Don't Want to Know", 195, 1L);
        Track track2 = new Track("Dreams", 257, 1L);
        track1Saved = new Track(1L, "I Don't Want to Know", 195, 1L);
        Track track2Saved = new Track(2L, "Dreams", 257, 1L);
        track1Edited = new Track(1L, "That Song All Day", 111, 4L);
        track1EditSave = new Track(1L, "That Song All Day", 111, 4L);
        List<Track> trackList = new ArrayList<>();
        trackList.add(track1Saved);
        trackList.add(track2Saved);

        doReturn(track1Saved).when(trackRepo).save(track1);
        doReturn(track1EditSave).when(trackRepo).save(track1Edited);
        doReturn(Optional.of(track1Saved)).when(trackRepo).findById(1L);
        doReturn(trackList).when(trackRepo).findAll();
        Optional<Track> nullTrack = Optional.ofNullable(null);
        doReturn(nullTrack).when(trackRepo).findById(3752L);
    }

    // Album CRUD Section
    @Test
    public void shouldSaveAlbum() {
        Album albumOutput = album1Saved;
        Album actualResult = service.createAlbum(album1);

        assertEquals(albumOutput, actualResult);
        verify(albumRepo).save(album1);
    }

    @Test
    public void shouldFindAllAlbums() {
        List<Album> fromService = service.findAllAlbums();
        Album index1 = fromService.get(0);
        assertEquals(2, fromService.size());
        assertEquals(index1, album1Saved);
    }

    @Test
    public void shouldFindAlbumById() {
        Album actualResult = service.findAlbumById(1L);
        assertEquals(album1Saved, actualResult);
        verify(albumRepo).findById(1L);
    }

    @Test
    public void shouldUpdateAlbum() {
        service.updateAlbum(album1Edited);
        verify(albumRepo).save(album1Edited);
    }

    @Test
    public void shouldDeleteAlbum() {
        service.deleteAlbum(1L);
        verify(albumRepo).deleteById(1L);
    }
    //  Album Exception Tests
    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenUpdatingAlbumWithInvalidId(){
        Album badId = new Album(3752L, "The Definitive Collection", "2000-01-01", new BigDecimal("19.99"), 2L, 1L);
        service.updateAlbum(badId);
        fail("We Failed the Test, Exception Not Thrown.");
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenDeletingAlbumWithInvalidId(){
        service.deleteAlbum(3752L);
        fail("We Failed the Test, Exception Not Thrown.");
    }

    // Artist CRUD Section
    @Test
    public void shouldSaveArtist() {
        Artist actualResult = service.createArtist(artist1);

        assertEquals(artist1Saved, actualResult);
        verify(artistRepo).save(artist1);
    }

    @Test
    public void shouldFindAllArtists() {
        List<Artist> fromService = service.findAllArtists();
        Artist index1 = fromService.get(0);
        assertEquals(2, fromService.size());
        assertEquals(index1, artist1Saved);
    }

    @Test
    public void shouldFindArtistById() {
        Artist actualResult = service.findArtistById(1L);

        assertEquals(artist1Saved, actualResult);
        verify(artistRepo).findById(1L);
    }

    @Test
    public void shouldUpdateArtist() {
        service.updateArtist(artist1Edited);
        verify(artistRepo).save(artist1Edited);
    }

    @Test
    public void shouldDeleteArtist() {
        service.deleteArtist(1L);
        verify(artistRepo).deleteById(1L);
    }
    //  Artist Exception Tests
    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenUpdatingArtistWithInvalidId(){
        Artist badId = new Artist(3752L, "That Guy", "ig-guy","tweet");
        service.updateArtist(badId);
        fail("We Failed the Test, Exception Not Thrown.");
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenDeletingArtistWithInvalidId(){
        service.deleteArtist(3752L);
        fail("We Failed the Test, Exception Not Thrown.");
    }
    // Label CRUD Section
    @Test
    public void shouldSaveLabel() {
        Label actualResult = service.createLabel(label1);

        assertEquals(label1Saved, actualResult);
        verify(labelRepo).save(label1);
    }

    @Test
    public void shouldFindAllLabels() {
        List<Label> fromService = service.findAllLabels();
        Label index1 = fromService.get(0);
        assertEquals(2, fromService.size());
        assertEquals(index1, label1Saved);
    }

    @Test
    public void shouldFindLabelRById() {
        Label actualResult = service.findLabelById(1L);

        assertEquals(label1Saved, actualResult);
        verify(labelRepo).findById(1L);
    }

    @Test
    public void shouldUpdateLabel() {
        service.updateLabel(label1Edited);

        verify(labelRepo).save(label1Edited);
    }

    @Test
    public void shouldDeleteLabel() {
        service.deleteLabel(1L);
        verify(labelRepo).deleteById(1L);
    }
    //  Label Exception Tests
    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenUpdatingLabelWithInvalidId(){
        Label badId = new Label(3752L, "That Compnany", "thoseguys.com");
        service.updateLabel(badId);
        fail("We Failed the Test, Exception Not Thrown.");
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenDeletingLabelWithInvalidId(){
        service.deleteLabel(3752L);
        fail("We Failed the Test, Exception Not Thrown.");
    }
    // Track CRUD Section
    @Test
    public void shouldSaveTrack() {
        Track actualResult = service.createTrack(track1);

        assertEquals(track1Saved, actualResult);
        verify(trackRepo).save(track1);
    }

    @Test
    public void shouldFindAllTracks() {
        List<Track> fromService = service.findAllTracks();
        Track index1 = fromService.get(0);
        assertEquals(2, fromService.size());
        assertEquals(index1, track1Saved);
    }

    @Test
    public void shouldFindTrackById() {
        Track actualResult = service.findTrackById(1L);

        assertEquals(track1Saved, actualResult);
        verify(trackRepo).findById(1L);
    }

    @Test
    public void shouldUpdateTrack() {

        service.updateTrack(track1Edited);

        verify(trackRepo).save(track1Edited);
    }

    @Test
    public void shouldDeleteTrack() {
        service.deleteTrack(1L);
        verify(trackRepo).deleteById(1L);
    }
    //  Track Exception Tests
    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenUpdatingTrackWithInvalidId(){
        Track badId = new Track(3752L,"That Song", 123, 1L);
        service.updateTrack(badId);
        fail("We Failed the Test, Exception Not Thrown.");
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionWhenDeletingTrackWithInvalidId(){
        service.deleteTrack(3752L);
        fail("We Failed the Test, Exception Not Thrown.");
    }
}