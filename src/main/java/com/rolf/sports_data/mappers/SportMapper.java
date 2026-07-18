package com.rolf.sports_data.mappers;

import com.rolf.sports_data.entities.SportEntity;
import com.rolf.sports_data.dto.sports.SportRequestDto;
import com.rolf.sports_data.dto.sports.SportResponsetDto;

public final class SportMapper {

    private SportMapper() {
    }

    /**
        Converts a SportEntity in a respose for sports
    */
    public static SportResponsetDto toResponse(SportEntity entity) {
        return new SportResponsetDto(
                entity.getId(),
                entity.getName(),
                entity.getSlug());
    }

    public static SportEntity toEntity(SportRequestDto dto) {
        return new SportEntity(
            dto.getName(),
            dto.getDescription()
        );
    }
}
