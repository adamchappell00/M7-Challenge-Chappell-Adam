package com.musicstore.musicstorecatalog.repository;

import com.musicstore.musicstorecatalog.model.Label;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LabelRepository extends JpaRepository<Label, Long> {

}