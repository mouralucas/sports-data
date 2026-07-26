package com.rolf.sports_data.dto.CompetitionStageTemplate;

import java.util.List;

import com.rolf.sports_data.dto.StageSlotTemplate.StageSlotTemplateDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CompetitionStageTemaplateResponseDto {
    

    public CompetitionStageTemaplateResponseDto(@NotNull Long id, CompetitionStageTemaplateResponseDto parentTemplate, @NotBlank String name,
            @NotBlank String stageType, @NotNull Integer displayOrder, Integer expectedParticipants,
            Integer expectedEvents, List<StageSlotTemplateDto> stageSlotTemplates) {
        this.id = id;
        this.parentTemplate = parentTemplate;
        this.name = name;
        this.stageType = stageType;
        this.displayOrder = displayOrder;
        this.expectedParticipants = expectedParticipants;
        this.expectedEvents = expectedEvents;
        this.stageSlotTemplates = stageSlotTemplates;
    }

    @NotNull
    private Long id;

    private CompetitionStageTemaplateResponseDto parentTemplate;

    @NotBlank
    private String name;

    @NotBlank
    private String stageType;

    @NotNull
    private Integer displayOrder;

    private Integer expectedParticipants;

    private Integer expectedEvents;

    private List<StageSlotTemplateDto> stageSlotTemplates;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CompetitionStageTemaplateResponseDto getParentTemplate() {
        return parentTemplate;
    }

    public void setParentTemplateId(CompetitionStageTemaplateResponseDto parentTemplateId) {
        this.parentTemplate = parentTemplateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStageType() {
        return stageType;
    }

    public void setStageType(String stageType) {
        this.stageType = stageType;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getExpectedParticipants() {
        return expectedParticipants;
    }

    public void setExpectedParticipants(Integer expectedParticipants) {
        this.expectedParticipants = expectedParticipants;
    }

    public Integer getExpectedEvents() {
        return expectedEvents;
    }

    public void setExpectedEvents(Integer expectedEvents) {
        this.expectedEvents = expectedEvents;
    }

    public List<StageSlotTemplateDto> getStageSlotTemplates() {
        return stageSlotTemplates;
    }

    public void setStageSlotTemplates(List<StageSlotTemplateDto> stageSlotTemplates) {
        this.stageSlotTemplates = stageSlotTemplates;
    }

    
}
