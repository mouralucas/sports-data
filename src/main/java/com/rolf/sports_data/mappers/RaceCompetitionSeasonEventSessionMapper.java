package com.rolf.sports_data.mappers;

import com.rolf.sports_data.dto.RaceCompetitionSeasonEventSession.RaceCompetitionSeasonEventSessionResponseDto;
import com.rolf.sports_data.entities.RaceCompetitionSeasonEventSessionEntity;

import java.util.List;

public class RaceCompetitionSeasonEventSessionMapper {
    public static RaceCompetitionSeasonEventSessionResponseDto toResponse(RaceCompetitionSeasonEventSessionEntity session) {
        return new RaceCompetitionSeasonEventSessionResponseDto(
                session.getEvent().getId(),
                session.getEvent().getName(),
                session.getId(),
                session.getName(),
                session.getType().getDescription(),
                session.getStartDate(),
                session.getEndDate()
        );
    }

    public static List<RaceCompetitionSeasonEventSessionResponseDto> toListResponse(List<RaceCompetitionSeasonEventSessionEntity> sessions) {
        return sessions.stream().map(RaceCompetitionSeasonEventSessionMapper::toResponse).toList();
    }
}
