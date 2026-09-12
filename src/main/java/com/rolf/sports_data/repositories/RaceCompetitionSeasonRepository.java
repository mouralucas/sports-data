package com.rolf.sports_data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rolf.sports_data.entities.RaceCompetitionSeasonEntity;

import java.util.Optional;

public interface RaceCompetitionSeasonRepository extends JpaRepository<RaceCompetitionSeasonEntity, Long>{
    Optional<RaceCompetitionSeasonEntity> findBySlug(String slug);
}
