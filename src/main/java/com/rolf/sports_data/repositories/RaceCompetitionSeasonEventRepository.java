package com.rolf.sports_data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rolf.sports_data.entities.RaceCompetitionSeasonEventEntity;

public interface RaceCompetitionSeasonEventRepository extends JpaRepository<RaceCompetitionSeasonEventEntity, Long> {
    List<RaceCompetitionSeasonEventEntity> findAllByCompetitionSeasonId(Long id);
}
