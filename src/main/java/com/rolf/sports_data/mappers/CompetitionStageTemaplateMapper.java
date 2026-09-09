package com.rolf.sports_data.mappers;

import java.util.List;

import org.springframework.stereotype.Component;

import com.rolf.sports_data.dto.competitionStageTemplate.CompetitionStageTemaplateResponseDto;
import com.rolf.sports_data.entities.CompetitionStageTemplateEntity;

@Component
public class CompetitionStageTemaplateMapper {

    private final StageSlotTemplateMapper stageSlotTemplateMapper;

    public CompetitionStageTemaplateMapper(StageSlotTemplateMapper stageSlotTemplateMapper) {
        this.stageSlotTemplateMapper = stageSlotTemplateMapper;
    }

    public CompetitionStageTemaplateResponseDto toResponse(
            CompetitionStageTemplateEntity competitionStageTemaplateEntity) {
        return new CompetitionStageTemaplateResponseDto(
                competitionStageTemaplateEntity.getId(),
                this.toParentResponse(competitionStageTemaplateEntity.getParentStageTemplate()),
                competitionStageTemaplateEntity.getName(),
                competitionStageTemaplateEntity.getStageType(),
                competitionStageTemaplateEntity.getDisplayOrder(),
                competitionStageTemaplateEntity.getExpectedParticipants(),
                competitionStageTemaplateEntity.getExpectedEvents(),
                stageSlotTemplateMapper.toListResponse(competitionStageTemaplateEntity.getStageSlotTemplates()));
    }

    public List<CompetitionStageTemaplateResponseDto> toListResponse(
            List<CompetitionStageTemplateEntity> competitionStageTemaplateEntities) {
        return competitionStageTemaplateEntities.stream()
                .map(this::toResponse)
                .toList();
    }

    private CompetitionStageTemaplateResponseDto toParentResponse(
            CompetitionStageTemplateEntity parent) {

        if (parent == null) {
            return null;
        }

        return new CompetitionStageTemaplateResponseDto(
                parent.getId(),
                null, // Don't map the parent's parent
                parent.getName(),
                parent.getStageType(),
                parent.getDisplayOrder(),
                parent.getExpectedParticipants(),
                parent.getExpectedEvents(),
                null);
    }
}
