package com.rolf.sports_data.entities;

import java.time.LocalDateTime;
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
@Table(name = "competition_event",
    indexes = {
        @Index(name = "idx_competition_event_competition_edition", columnList = "competition_edition_id"),
        @Index(name = "idx_competition_event_competition_stage", columnList = "competition_stage_id"),
        @Index(name = "idx_competition_event_status", columnList = "status"),
        @Index(name = "idx_competition_event_scheduled_at", columnList = "scheduled_at"),
    }
)
public class CompetitionEventEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "competition_edition_id")
    private CompetitionEditionEntity competitionEdition;

    @ManyToOne
    @JoinColumn(name = "competition_stage_id")
    private CompetitionStageEntity competitionStage;

    @ManyToOne
    @JoinColumn(name = "competition_event_template_id", nullable = true)
    private CompetitionEventTemplateEntity competitionEventTemplate;

    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = true)
    private VenueEntity venue;

    @Column(name = "event_order")
    private Integer eventOrder;

    @Column(name = "status", length = 30)
    private String status;

    @Column(name = "scheduled_at", nullable = true)
    private LocalDateTime scheduledAt;

    @Column(name = "started_at", nullable = true)
    private LocalDateTime startedAt;

    @Column(name = "finished_at", nullable = true)
    private LocalDateTime finishedAt;

    @OneToMany(mappedBy = "competitionEvent")
    private List<CompetitionEventParticipantEntity> competitionEventParticipants = new ArrayList<>();

    @OneToMany(mappedBy = "competitionEvent")
    private List<EventActionEntity> eventActions = new ArrayList<>();
}
