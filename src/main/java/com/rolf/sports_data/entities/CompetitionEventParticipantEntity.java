package com.rolf.sports_data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "competition_event_participant")
public class CompetitionEventParticipantEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "competition_event_id")
    private CompetitionEventEntity competitionEvent;

    @ManyToOne
    @JoinColumn(name = "stage_slot_id", nullable = true)
    private StageSlotEntity stageSlot;

    @Column(name = "participant_role", length = 40)
    private String participantRole;

    @Column(name = "lane", nullable = true)
    private Integer lane;

    @Column(name = "result_status", length = 40, nullable = true)
    private String resultStatus;
}