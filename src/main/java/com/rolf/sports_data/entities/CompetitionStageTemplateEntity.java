package com.rolf.sports_data.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "competition_stage_template", indexes = {
        @Index(name = "idx_stage_template_template", columnList = "competition_template_id"),
        @Index(name = "idx_stage_template_parent", columnList = "parent_stage_template_id"),
        @Index(name = "idx_stage_template_order", columnList = "display_order")
})
public class CompetitionStageTemplateEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "competition_template_id", nullable = true)
    private CompetitionTemplateEntity competitionTemplate;

    @ManyToOne
    @JoinColumn(name = "parent_stage_template_id", nullable = true)
    private CompetitionStageTemplateEntity parent_stage_template;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "stage_type", length = 40)
    private String stageType;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "expected_participants", nullable = true)
    private Integer expectedParticipants;

    @Column(name = "expected_events", nullable = true)
    private Integer expectedEvents;

    @OneToMany(mappedBy = "competitionStageTemplate")
    private List<StageSlotTemplateEntity> stageSlotTemplates = new ArrayList<>();

    @OneToMany(mappedBy = "competitionStageTemplate")
    private List<CompetitionEventTemplateEntity> competitionEventTemplates = new ArrayList<>();

    public CompetitionTemplateEntity getCompetitionTemplate() {
        return competitionTemplate;
    }

    public void setCompetitionTemplate(CompetitionTemplateEntity competitionTemplate) {
        this.competitionTemplate = competitionTemplate;
    }

    public CompetitionStageTemplateEntity getParentStageTemplate() {
        return parent_stage_template;
    }

    public void setParent_stage_template(CompetitionStageTemplateEntity parent_stage_template) {
        this.parent_stage_template = parent_stage_template;
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

    public List<StageSlotTemplateEntity> getStageSlotTemplates() {
        return stageSlotTemplates;
    }

    public void setStageSlotTemplates(List<StageSlotTemplateEntity> stageSlotTemplates) {
        this.stageSlotTemplates = stageSlotTemplates;
    }

    public List<CompetitionEventTemplateEntity> getCompetitionEventTemplates() {
        return competitionEventTemplates;
    }

    public void setCompetitionEventTemplates(List<CompetitionEventTemplateEntity> competitionEventTemplates) {
        this.competitionEventTemplates = competitionEventTemplates;
    }

    
}
