package com.rolf.sports_data.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "event_action",
    indexes = {
        @Index(name = "idx_event_action_competition_event", columnList = "competition_event_id"),
        @Index(name = "idx_event_action_type", columnList = "action_type"),
        @Index(name = "idx_event_action_participant", columnList = "participant_id"),
        @Index(name = "idx_event_action_occurred_at", columnList = "occurred_at"),
    }
)
public class EventActionEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "competition_event_id")
    private CompetitionEventEntity competitionEvent;

    @ManyToOne
    @JoinColumn(name = "competition_event_participant_id", nullable = true)
    private CompetitionEventParticipantEntity competitionEventParticipant;

    @ManyToOne
    @JoinColumn(name = "participant_id", nullable = true)
    private ParticipantEntity participant;

    @Column(name = "action_type", length = 50)
    private String actionType; 

    @Column(name = "occurred_at", nullable = true)
    private LocalDateTime occurredAt;

    @Column(name = "sequence_number", nullable = true)
    private Integer sequenceNumber;

    @Column(name = "value", nullable = true)
    private Double value;

    @Column(name = "metadata", columnDefinition = "jsonb", nullable = true)
    @JdbcTypeCode(SqlTypes.JSON)
    private String metadata;
}

