package com.rolf.sports_data.mappers;

import java.util.List;

import com.rolf.sports_data.dto.raceCompetitionSeasonEvent.RaceCompetitionSeasonEventRequest;
import com.rolf.sports_data.dto.raceCompetitionSeasonEvent.RaceCompetitionSeasonEventResponseDto;
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

    public static RaceCompetitionSeasonEventEntity toEntity(RaceCompetitionSeasonEventRequest requestDto) {
        return new RaceCompetitionSeasonEventEntity(
            requestDto.getName(),
            requestDto.getStartDate(),
            requestDto.getEndDate()
        );
    }
}
