package com.rolf.sports_data.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.rolf.sports_data.dto.StageSlotTemplate.StageSlotTemplateDto;
import com.rolf.sports_data.entities.StageSlotTemplateEntity;

@Component
public class StageSlotTemplateMapper {
    public StageSlotTemplateDto toResponse(StageSlotTemplateEntity stageSlotTemplateEntity) {
        return new StageSlotTemplateDto(
                stageSlotTemplateEntity.getId(),
                stageSlotTemplateEntity.getSlotOrder(),
                stageSlotTemplateEntity.getSlotType(),
                stageSlotTemplateEntity.getSourcePosition(),
                stageSlotTemplateEntity.getDescription());
    }

    public List<StageSlotTemplateDto> toListResponse(List<StageSlotTemplateEntity> sports) {
        return sports.stream()
                .map(this::toResponse)
                .toList();
    }

}
