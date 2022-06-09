package com.musicstore.musicstorerecommendations.repository;

import com.musicstore.musicstorerecommendations.model.ArtistRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRecommendationRepository extends JpaRepository<ArtistRecommendation, Long> {

}