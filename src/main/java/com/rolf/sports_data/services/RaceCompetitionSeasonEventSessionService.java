package com.rolf.sports_data.services;

import com.rolf.sports_data.dto.RaceCompetitionSeasonEventSession.RaceCompetitionSeasonEventSessionResponseDto;
import com.rolf.sports_data.entities.RaceCompetitionSeasonEventSessionEntity;
import com.rolf.sports_data.mappers.RaceCompetitionSeasonEventSessionMapper;
import com.rolf.sports_data.repositories.RaceCompetitionSeasonEventSessionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaceCompetitionSeasonEventSessionService {
    private final RaceCompetitionSeasonEventSessionRepository raceCompetitionSeasonEventSessionRepository;

    public RaceCompetitionSeasonEventSessionService(RaceCompetitionSeasonEventSessionRepository raceCompetitionSeasonEventSessionRepository) {
        this.raceCompetitionSeasonEventSessionRepository = raceCompetitionSeasonEventSessionRepository;
    }

    // Used only to check information
    public List<RaceCompetitionSeasonEventSessionResponseDto> fetchAllSessions() {
        List<RaceCompetitionSeasonEventSessionEntity> sessions = raceCompetitionSeasonEventSessionRepository.findAll();

        return RaceCompetitionSeasonEventSessionMapper.toListResponse(sessions);
    }

    public List<RaceCompetitionSeasonEventSessionResponseDto> fetchAllSessionsByEvent(Long eventId) {
        List<RaceCompetitionSeasonEventSessionEntity> sessions = raceCompetitionSeasonEventSessionRepository.findAllByEventId(eventId);
        return RaceCompetitionSeasonEventSessionMapper.toListResponse(sessions);
    }
}
