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
@Table(name = "competition_stage", indexes = {
        @Index(name = "idx_competition_stage_edition", columnList = "competition_edition_id"),
        @Index(name = "idx_competition_stage_display_order", columnList = "display_order"),
        @Index(name = "idx_competition_stage_status", columnList = "status")
})
public class CompetitionStageEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "competition_edition_id")
    private CompetitionEditionEntity competitionEdition;

    // @ManyToOne
    // @JoinColumn(name = "competition_stage_template_id")
    // private CompetitionStageTemplateEntity competitionStageTemplate;

    @ManyToOne
    @JoinColumn(name = "parent_stage_id", nullable = true)
    private CompetitionStageEntity parentStage;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "stage_type", length = 40)
    private String stageType;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "status", length = 30)
    private String status;

    @OneToMany(mappedBy = "competitionStage")
    private List<StageSlotEntity> stageSlots = new ArrayList<>();

    @OneToMany(mappedBy = "competitionStage")
    private List<CompetitionEventEntity> competitionEvents = new ArrayList<>();
}
