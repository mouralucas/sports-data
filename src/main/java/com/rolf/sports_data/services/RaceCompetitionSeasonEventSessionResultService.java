package com.rolf.sports_data.services;

import com.rolf.sports_data.dto.RaceCompetitionSeasonEventSessionResult.RaceCompetitionSeasonEventSessionResultCreateRequest;
import com.rolf.sports_data.dto.RaceCompetitionSeasonEventSessionResult.RaceCompetitionSeasonEventSessionResultResponseDto;
import com.rolf.sports_data.entities.RaceCompetitionSeasonEventEntryEntity;
import com.rolf.sports_data.entities.RaceCompetitionSeasonEventSessionEntity;
import com.rolf.sports_data.entities.RaceCompetitionSeasonEventSessionResultEntity;
import com.rolf.sports_data.mappers.RaceCompetitionSeasonEventSessionResultMapper;
import com.rolf.sports_data.repositories.RaceCompetitionSeasonEventEntryRepository;
import com.rolf.sports_data.repositories.RaceCompetitionSeasonEventSessionRepository;
import com.rolf.sports_data.repositories.RaceCompetitionSeasonEventSessionResultRepository;
import org.springframework.stereotype.Service;

@Service
public class RaceCompetitionSeasonEventSessionResultService {
    private final RaceCompetitionSeasonEventSessionResultRepository raceCompetitionSeasonEventSessionResultRepository;
    private final RaceCompetitionSeasonEventSessionRepository raceCompetitionSeasonEventSessionRepository;
    private final RaceCompetitionSeasonEventEntryRepository raceCompetitionSeasonEventEntryRepository;

    public RaceCompetitionSeasonEventSessionResultService(
            RaceCompetitionSeasonEventSessionResultRepository raceCompetitionSeasonEventSessionResultRepository,
            RaceCompetitionSeasonEventSessionRepository raceCompetitionSeasonEventSessionRepository,
            RaceCompetitionSeasonEventEntryRepository raceCompetitionSeasonEventEntryEntity
    ){
        this.raceCompetitionSeasonEventSessionRepository = raceCompetitionSeasonEventSessionRepository;
        this.raceCompetitionSeasonEventEntryRepository = raceCompetitionSeasonEventEntryEntity;
        this.raceCompetitionSeasonEventSessionResultRepository = raceCompetitionSeasonEventSessionResultRepository;
    }


    public RaceCompetitionSeasonEventSessionResultResponseDto createSessionResult (RaceCompetitionSeasonEventSessionResultCreateRequest result) {
        RaceCompetitionSeasonEventSessionResultEntity newResult = RaceCompetitionSeasonEventSessionResultMapper.toEntity(result);

        RaceCompetitionSeasonEventSessionEntity session = raceCompetitionSeasonEventSessionRepository.getReferenceById(result.getSessionId());
        RaceCompetitionSeasonEventEntryEntity entry = raceCompetitionSeasonEventEntryRepository.getReferenceById(result.getEntryId());

        newResult.setSession(session);
        newResult.setEntry(entry);

        RaceCompetitionSeasonEventSessionResultEntity r = raceCompetitionSeasonEventSessionResultRepository.save(newResult);

        return RaceCompetitionSeasonEventSessionResultMapper.toResponse(r);
    }
}
