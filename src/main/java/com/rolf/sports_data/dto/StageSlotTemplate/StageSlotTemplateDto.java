package com.rolf.sports_data.dto.stageSlotTemplate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class StageSlotTemplateDto {

    // Constructor to not set competittionStageTemplate
    public StageSlotTemplateDto(@NotNull Long id, @NotNull Integer slotOrder, @NotBlank String slotType,
            Integer sourcePosition, String description) {
        this.id = id;
        this.slotOrder = slotOrder;
        this.slotType = slotType;
        this.sourcePosition = sourcePosition;
        this.description = description;
    }

    @NotNull
    private Long id;

    @NotNull
    private Long competitionStageTemplate;

    @NotNull
    private Integer slotOrder;

    @NotBlank
    private String slotType;

    private Integer sourcePosition;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCompetitionStageTemplate() {
        return competitionStageTemplate;
    }

    public void setCompetitionStageTemplate(Long competitionStageTemplate) {
        this.competitionStageTemplate = competitionStageTemplate;
    }

    public Integer getSlotOrder() {
        return slotOrder;
    }

    public void setSlotOrder(Integer slotOrder) {
        this.slotOrder = slotOrder;
    }

    public String getSlotType() {
        return slotType;
    }

    public void setSlotType(String slotType) {
        this.slotType = slotType;
    }

    public Integer getSourcePosition() {
        return sourcePosition;
    }

    public void setSourcePosition(Integer sourcePosition) {
        this.sourcePosition = sourcePosition;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
