package com.rolf.sports_data.repositories;

import com.rolf.sports_data.entities.RaceCompetitionSeasonEventSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaceCompetitionSeasonEventSessionRepository extends JpaRepository<RaceCompetitionSeasonEventSessionEntity, Long> {
    List<RaceCompetitionSeasonEventSessionEntity> findAllByEventId(Long eventId);
}
