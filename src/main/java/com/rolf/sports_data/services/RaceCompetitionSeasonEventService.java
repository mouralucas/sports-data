package com.rolf.sports_data.services;

import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolf.sports_data.dto.raceCompetitionSeasonEvent.RaceCompetitionSeasonEventRequest;
import com.rolf.sports_data.dto.raceCompetitionSeasonEvent.RaceCompetitionSeasonEventResponseDto;
import com.rolf.sports_data.entities.RaceCompetitionSeasonEntity;
import com.rolf.sports_data.entities.RaceCompetitionSeasonEventEntity;
import com.rolf.sports_data.mappers.RaceCompetitionSeasonEventMapper;
import com.rolf.sports_data.repositories.RaceCompetitionSeasonEventRepository;
import com.rolf.sports_data.repositories.RaceCompetitionSeasonRepository;

@Service
public class RaceCompetitionSeasonEventService {
    private final RaceCompetitionSeasonEventRepository raceCompetitionSeasonEventRepository;
    private final RaceCompetitionSeasonRepository raceCompetitionSeasonRepository;

    @Autowired
    public RaceCompetitionSeasonEventService(
            RaceCompetitionSeasonEventRepository raceCompetitionSeasonEventRepository,
            RaceCompetitionSeasonRepository raceCompetitionSeasonRepository) {
        this.raceCompetitionSeasonEventRepository = raceCompetitionSeasonEventRepository;
        this.raceCompetitionSeasonRepository = raceCompetitionSeasonRepository;
    }

    public List<RaceCompetitionSeasonEventResponseDto> fetchAllSeasonEvents(String seasonId) {
        List<RaceCompetitionSeasonEventEntity> events = raceCompetitionSeasonEventRepository
                .findAllByCompetitionSeasonSlug(seasonId);

        return RaceCompetitionSeasonEventMapper.toListResponse(events);
    }

    public RaceCompetitionSeasonEventResponseDto createSeasonEvent(RaceCompetitionSeasonEventRequest event, String seasonSlug) {
        RaceCompetitionSeasonEventEntity newEvent = RaceCompetitionSeasonEventMapper.toEntity(event);

        RaceCompetitionSeasonEntity season = raceCompetitionSeasonRepository
                .findBySlug(seasonSlug)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Season not found: " + seasonSlug
                ));

        newEvent.setCompetitionSeason(season);

        RaceCompetitionSeasonEventEntity savedEvent = raceCompetitionSeasonEventRepository.save(newEvent);

        return RaceCompetitionSeasonEventMapper.toResponse(savedEvent);
    }
}
