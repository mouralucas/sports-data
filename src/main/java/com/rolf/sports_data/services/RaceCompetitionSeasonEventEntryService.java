package com.rolf.sports_data.services;

import com.rolf.sports_data.dto.raceCompetitonSeasonEventEntry.RaceCompetitionSeasonEventEntryResponseDto;
import com.rolf.sports_data.entities.RaceCompetitionSeasonEventEntryEntity;
import com.rolf.sports_data.mappers.RaceCompetitionSeasonEventEntryMapper;
import com.rolf.sports_data.repositories.RaceCompetitionSeasonEventEntryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaceCompetitionSeasonEventEntryService {
    private final RaceCompetitionSeasonEventEntryRepository raceCompetitionSeasonEventEntryRepository;

    public RaceCompetitionSeasonEventEntryService(RaceCompetitionSeasonEventEntryRepository raceCompetitionSeasonEventEntryRepository) {
        this.raceCompetitionSeasonEventEntryRepository = raceCompetitionSeasonEventEntryRepository;
    }

    public List<RaceCompetitionSeasonEventEntryResponseDto> fetchAllCompetitionsEntryByEvent(Long event_id) {
        List<RaceCompetitionSeasonEventEntryEntity> entries = raceCompetitionSeasonEventEntryRepository.findAllByEventId(event_id);
        return RaceCompetitionSeasonEventEntryMapper.toListResponse(entries);
    }
}
