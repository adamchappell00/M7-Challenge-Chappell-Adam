package com.musicstore.musicstorecatalog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Objects;


@Entity
@Table(name="track")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Track {

    // FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "track_id")
    private Long id;

    @NotNull(message = "Tracks must have a title.")
    @NotBlank(message = "Track title cannot be empty.")
    @Size(max = 50, message = "Track title must be less than 50 characters.")
    @Column(name = "title")
    private String title;

    @NotNull(message = "Tracks must have a run time.")
    @Min(value = 0, message="Track run time must be a positive value in seconds.")
    private Integer runTime;


    // FOREIGN KEYS
    @NotNull(message = "Tracks must have an album id.")
    @Column(name="album_id")
    private Long albumId;


    // CONSTRUCTORS
    public Track(){}

    public Track(String title, Integer runTime, Long albumId) {
        this.title = title;
        this.runTime = runTime;
        this.albumId = albumId;
    }

    public Track(Long id, String title, Integer runTime, Long albumId) {
        this.id = id;
        this.title = title;
        this.runTime = runTime;
        this.albumId = albumId;
    }

    // STANDARD METHODS
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return Objects.equals(id, track.id) && Objects.equals(title, track.title) && Objects.equals(runTime, track.runTime) && Objects.equals(albumId, track.albumId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, runTime, albumId);
    }

    @Override
    public String toString() {
        return "Track{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", runTime=" + runTime +
                ", albumId=" + albumId +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getRunTime() {
        return runTime;
    }

    public void setRunTime(Integer runTime) {
        this.runTime = runTime;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }
}