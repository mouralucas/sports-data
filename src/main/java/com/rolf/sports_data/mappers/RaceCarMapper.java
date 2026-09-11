package com.rolf.sports_data.mappers;

import java.util.List;

import com.rolf.sports_data.dto.raceCar.RaceCarResponseDto;
import com.rolf.sports_data.entities.RaceCarEntity;

public class RaceCarMapper {

    public static RaceCarResponseDto toResponse(RaceCarEntity raceCar) {
        return new RaceCarResponseDto(
                raceCar.getId(),
                raceCar.getConstructor().getId(),
                raceCar.getConstructor().getName(),
                raceCar.getNumber(),
                raceCar.getCarClass().getDescription(),
                raceCar.getChassis(),
                raceCar.getYear());
    }

    public static List<RaceCarResponseDto> toListResponse(List<RaceCarEntity> participants) {
        return participants.stream()
                .map(RaceCarMapper::toResponse)
                .toList();
    }
}
