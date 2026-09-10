package com.rolf.sports_data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolf.sports_data.dto.CompetitionSeasonEvent.RaceCompetitionSeasonEventResponseDto;
import com.rolf.sports_data.entities.RaceCompetitionSeasonEventEntity;
import com.rolf.sports_data.mappers.RaceCompetitionSeasonEventMapper;
import com.rolf.sports_data.repositories.RaceCompetitionSeasonEventRepository;

@Service
public class RaceCompetitionSeasonEventService {
    private RaceCompetitionSeasonEventRepository raceCompetitionSeasonEventRepository;

    @Autowired
    public RaceCompetitionSeasonEventService(
            RaceCompetitionSeasonEventRepository raceCompetitionSeasonEventRepository) {
        this.raceCompetitionSeasonEventRepository = raceCompetitionSeasonEventRepository;
    }

    public List<RaceCompetitionSeasonEventResponseDto> fetchAllSeasonEvents(Long seasonId) {
        List<RaceCompetitionSeasonEventEntity> events = raceCompetitionSeasonEventRepository
                .findAllByCompetitionSeasonId(seasonId);

        return RaceCompetitionSeasonEventMapper.toListResponse(events);
    }
}
