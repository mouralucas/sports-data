package com.rolf.sports_data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "stage_slot",
    indexes = {
        @Index(name = "idx_stage_slot_stage", columnList = "competition_stage_id"),
        @Index(name = "idx_stage_slot_competition_entry", columnList = "competition_entry_id"),
    }
)
public class StageSlotEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "competition_stage_id")
    private CompetitionStageEntity competitionStage;

    @Column(name = "slot_order")
    private Integer slotOrder;
}
