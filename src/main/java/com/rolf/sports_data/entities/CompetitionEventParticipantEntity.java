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
@Table(name = "competition_event_participant", 
    indexes = {
        @Index(name = "idx_competition_event_participant_competition_event", columnList = "competition_event_id"),
        @Index(name = "idx_competition_event_participant_competition_entry", columnList = "competition_entry_id"),
        @Index(name = "idx_competition_event_participant_result_status", columnList = "result_status")
    }
)
public class CompetitionEventParticipantEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "competition_event_id")
    private CompetitionEventEntity competitionEvent;

    @ManyToOne
    @JoinColumn(name = "competition_entry_id")
    private CompetitionEntryEntity competitionEntry;

    @ManyToOne
    @JoinColumn(name = "stage_slot_id", nullable = true)
    private StageSlotEntity stageSlot;

    @Column(name = "participant_role", length = 40)
    private String participantRole;

    @Column(name = "lane", nullable = true)
    private Integer lane;

    @Column(name = "result_status", length = 40, nullable = true)
    private String resultStatus;

    @OneToMany(mappedBy = "competitionEventParticipant")
    private List<EventActionEntity> eventActions = new ArrayList<>();
}