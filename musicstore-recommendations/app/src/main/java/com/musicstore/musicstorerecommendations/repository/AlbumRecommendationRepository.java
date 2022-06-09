package com.musicstore.musicstorerecommendations.repository;

import com.musicstore.musicstorerecommendations.model.AlbumRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AlbumRecommendationRepository extends JpaRepository<AlbumRecommendation, Long> {

}