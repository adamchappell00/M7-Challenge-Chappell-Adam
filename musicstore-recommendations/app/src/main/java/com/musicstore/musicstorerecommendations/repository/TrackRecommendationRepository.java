package com.musicstore.musicstorerecommendations.repository;

import com.musicstore.musicstorerecommendations.model.TrackRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackRecommendationRepository extends JpaRepository<TrackRecommendation, Long> {

}