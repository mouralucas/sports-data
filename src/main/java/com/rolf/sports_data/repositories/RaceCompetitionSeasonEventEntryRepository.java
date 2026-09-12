package com.rolf.sports_data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rolf.sports_data.entities.RaceCompetitionSeasonEventEntryEntity;

import java.util.List;

public interface RaceCompetitionSeasonEventEntryRepository extends JpaRepository<RaceCompetitionSeasonEventEntryEntity, Long> {
    List<RaceCompetitionSeasonEventEntryEntity> findAllByEventId(Long id);
}


