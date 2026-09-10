package com.rolf.sports_data.mappers;

import java.util.List;

import com.rolf.sports_data.dto.CompetitionSeasonEvent.RaceCompetitionSeasonEventResponseDto;
import com.rolf.sports_data.entities.RaceCompetitionSeasonEventEntity;

public class RaceCompetitionSeasonEventMapper {
    public static RaceCompetitionSeasonEventResponseDto toResponse(RaceCompetitionSeasonEventEntity entity) {
        return new RaceCompetitionSeasonEventResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getStartDate(),
                entity.getEndDate());
    }

    public static List<RaceCompetitionSeasonEventResponseDto> toListResponse(
            List<RaceCompetitionSeasonEventEntity> event) {
        return event.stream()
                .map(RaceCompetitionSeasonEventMapper::toResponse)
                .toList();
    }
}
