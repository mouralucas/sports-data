package com.rolf.sports_data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Deprecated
@Entity
@Table(name = "stage_slot_template",
    indexes = {
        @Index(name = "idx_slot_template_stage", columnList = "competition_stage_template_id"),
        @Index(name = "idx_slot_template_source_stage", columnList = "source_stage_template_id"),
    }
)
public class StageSlotTemplateEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "competition_stage_template_id")
    private CompetitionStageTemplateEntity competitionStageTemplate;

    @Column(name = "slot_order")
    private Integer slotOrder;

    @Column(name = "slot_type", length = 40)
    private String slotType;

    @ManyToOne
    @JoinColumn(name = "source_stage_template_id", nullable = true)
    private CompetitionStageTemplateEntity sourceStageTemplate;

    @Column(name = "source_position", nullable = true)
    private Integer sourcePosition;

    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    private String description;

    public CompetitionStageTemplateEntity getCompetitionStageTemplate() {
        return competitionStageTemplate;
    }

    public void setCompetitionStageTemplate(CompetitionStageTemplateEntity competitionStageTemplate) {
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

    public CompetitionStageTemplateEntity getSourceStageTemplate() {
        return sourceStageTemplate;
    }

    public void setSourceStageTemplate(CompetitionStageTemplateEntity sourceStageTemplate) {
        this.sourceStageTemplate = sourceStageTemplate;
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
