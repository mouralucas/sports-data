package com.rolf.sports_data.mappers;

import java.util.List;

import com.rolf.sports_data.dto.sports.SportRequestDto;
import com.rolf.sports_data.dto.sports.SportResponseDto;
import com.rolf.sports_data.entities.SportEntity;

public final class SportMapper {

    public SportMapper() {
    }

    /**
     * Converts a SportEntity in a respose for sports
     */
    public static SportResponseDto toResponse(SportEntity entity) {
        return new SportResponseDto(
                entity.getId(),
                entity.getName(),
                entity.getSlug(),
                entity.getDescription(),
                entity.isActive()
            );
    }

    public static List<SportResponseDto> toListResponse(List<SportEntity> sports) {
        return sports.stream()
                .map(SportMapper::toResponse)
                .toList();
    }

    public static SportEntity toEntity(SportRequestDto requestDto) {
        return new SportEntity(
                requestDto.getName(),
                requestDto.getDescription());
    }
}
