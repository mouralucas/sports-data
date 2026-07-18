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

}
