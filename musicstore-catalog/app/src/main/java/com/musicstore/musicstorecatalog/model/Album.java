package com.musicstore.musicstorecatalog.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="album")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Album {

    // FIELDS
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "album_id")
    private Long id;

    @NotNull(message = "Album must have a title.")
    @NotBlank(message = "Album title cannot be empty.")
    @Size(max = 50, message = "Album title must be less than 50 characters.")
    @Column(name = "title")
    private String title;


    @NotNull(message="Album must have a release date (YYYY-MM-DD).")
    private String releaseDate;

    @NotNull(message="Album must have a price.")
    @Min(value = 0, message="Album price must be a positive value.")
    @Column(columnDefinition = "Decimal(5,2)")
    private BigDecimal listPrice;

    // FOREIGN KEYS
    @NotNull(message="Album must have an associated artist.")
    @Column(name="artist_id")
    private Long artistId;

    @NotNull(message="Album must have an associated label.")
    @Column(name="label_id")
    private Long labelId;

    // CONSTRUCTORS
    public Album(){}

    public Album(String title, String releaseDate, BigDecimal listPrice, Long artistId, Long labelId) {
        this.title = title;
        this.releaseDate = releaseDate;
        this.listPrice = listPrice;
        this.artistId = artistId;
        this.labelId = labelId;
    }

    public Album(Long id, String title, String releaseDate, BigDecimal listPrice, Long artistId, Long labelId) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.listPrice = listPrice;
        this.artistId = artistId;
        this.labelId = labelId;
    }

    // STANDARD METHODS

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Album album = (Album) o;
        return Objects.equals(id, album.id) && Objects.equals(title, album.title) && Objects.equals(releaseDate, album.releaseDate) && Objects.equals(listPrice, album.listPrice) && Objects.equals(artistId, album.artistId) && Objects.equals(labelId, album.labelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, releaseDate, listPrice, artistId, labelId);
    }

    @Override
    public String toString() {
        return "Album{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", listPrice=" + listPrice +
                ", artistId=" + artistId +
                ", labelId=" + labelId +
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public BigDecimal getListPrice() {
        return listPrice;
    }

    public void setListPrice(BigDecimal listPrice) {
        this.listPrice = listPrice;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public Long getLabelId() {
        return labelId;
    }

    public void setLabelId(Long labelId) {
        this.labelId = labelId;
    }
}