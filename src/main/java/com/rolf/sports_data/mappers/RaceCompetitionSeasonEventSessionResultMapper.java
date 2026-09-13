package com.rolf.sports_data.mappers;

import com.rolf.sports_data.dto.RaceCompetitionSeasonEventSessionResult.RaceCompetitionSeasonEventSessionResultCreateRequest;
import com.rolf.sports_data.dto.RaceCompetitionSeasonEventSessionResult.RaceCompetitionSeasonEventSessionResultResponseDto;
import com.rolf.sports_data.entities.RaceCompetitionSeasonEventSessionResultEntity;
import com.rolf.sports_data.utils.DateTimeUtils;

public class RaceCompetitionSeasonEventSessionResultMapper {

    public static RaceCompetitionSeasonEventSessionResultResponseDto toResponse(RaceCompetitionSeasonEventSessionResultEntity result) {
        return new RaceCompetitionSeasonEventSessionResultResponseDto(
                result.getSession().getId(),
                result.getSession().getName(),
                result.getEntry().getId(),
                result.getEntry().getParticipant().getId(),
                result.getEntry().getParticipant().getName(),
                result.getStatus().getDescription(),
                result.getTime(),
                result.getLaps()
        );
    }

    public static RaceCompetitionSeasonEventSessionResultEntity toEntity(RaceCompetitionSeasonEventSessionResultCreateRequest result) {
        Long formattedTime = DateTimeUtils.convertHoursToMilliseconds(result.getTime());

        return new RaceCompetitionSeasonEventSessionResultEntity(
                result.getStatus(),
                formattedTime,
                result.getLaps()
        );
    }
}