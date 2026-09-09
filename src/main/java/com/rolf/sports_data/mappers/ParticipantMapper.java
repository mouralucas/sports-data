package com.rolf.sports_data.mappers;

import java.util.List;

import com.rolf.sports_data.dto.participants.ParticipantRequestDto;
import com.rolf.sports_data.dto.participants.ParticipantResponseDto;
import com.rolf.sports_data.entities.ParticipantEntity;

public class ParticipantMapper {
    public static ParticipantResponseDto toResponse(ParticipantEntity entity) {
        return new ParticipantResponseDto(
                entity.getName(),
                entity.getAcronym(),
                entity.getShortName(),
                entity.getParticipantType(),
                entity.isStatus());
    }

    public static List<ParticipantResponseDto> toListResponse(List<ParticipantEntity> participants) {
        return participants.stream()
                .map(ParticipantMapper::toResponse)
                .toList();
    }

    public static ParticipantEntity toEntity(ParticipantRequestDto dto) {
        return new ParticipantEntity(
                dto.getParticipantType(),
                dto.getName(),
                dto.getShortName(),
                dto.getAcronym(),
                null,
                true
        );
    }
}
