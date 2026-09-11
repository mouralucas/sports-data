package com.rolf.sports_data.mappers;

import java.util.List;

import com.rolf.sports_data.dto.contructor.ConstructorResponseDto;
import com.rolf.sports_data.entities.ConstructorEntity;

public class ConstructorMapper {

    public static ConstructorResponseDto toResponse(ConstructorEntity constructor) {
        return new ConstructorResponseDto(constructor.getId(),
                constructor.getName(),
                constructor.getDescription(),
                constructor.getYear());
    }

    public static List<ConstructorResponseDto> toListResponse(List<ConstructorEntity> constructors) {
        return constructors.stream().map(ConstructorMapper::toResponse).toList();
    }
}
