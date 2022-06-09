package com.musicstore.musicstorecatalog.repository;

import com.musicstore.musicstorecatalog.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

}