package com.musicstore.musicstorerecommendations.repository;

import com.musicstore.musicstorerecommendations.model.LabelRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRecommendationRepository extends JpaRepository<LabelRecommendation, Long> {

}