package com.rolf.sports_data.mappers;

import com.rolf.sports_data.dto.raceCompetitonSeasonEventEntry.RaceCompetitionSeasonEventEntryResponseDto;
import com.rolf.sports_data.entities.RaceCompetitionSeasonEventEntryEntity;

import java.util.List;

public class RaceCompetitionSeasonEventEntryMapper {
    public static RaceCompetitionSeasonEventEntryResponseDto toResponse(RaceCompetitionSeasonEventEntryEntity entry) {
        return new RaceCompetitionSeasonEventEntryResponseDto(
                entry.getId(),
                entry.getEvent().getId(),
                entry.getEvent().getName(),
                entry.getParticipant().getId(),
                entry.getParticipant().getName(),
                entry.getConstructor().getId(),
                entry.getConstructor().getName(),
                entry.getCar().getId(),
                entry.getCar().getNumber()
        );
    }

    public static List<RaceCompetitionSeasonEventEntryResponseDto> toListResponse(List<RaceCompetitionSeasonEventEntryEntity> entries) {
        return entries.stream().map(RaceCompetitionSeasonEventEntryMapper::toResponse).toList();
    }
}
