package com.rolf.sports_data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rolf.sports_data.entities.RaceCompetitionSeasonEventEntity;

public interface CompetitionSeasonEventRepository extends JpaRepository<RaceCompetitionSeasonEventEntity, Long> {

}
