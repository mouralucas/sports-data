package com.rolf.sports_data.entities;

import com.rolf.sports_data.enums.EventParticipantSideEnum;
import com.rolf.sports_data.enums.EventParticipantSourceTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "competittion_event_participant_template")
public class CompetitionEventParticipantTemplateEntity extends BaseEntity {

    @Column(name = "competition_event_template_id")
    private CompetitionEventTemplateEntity competitionEventTemplate;

    @Enumerated(EnumType.STRING)
    @Column(name = "side", length = 20)
    private EventParticipantSideEnum side;

    @Enumerated(EnumType.STRING)
    @Column(name = "source_type", length = 50)
    private EventParticipantSourceTypeEnum sourceType;

    // @ManyToOne
    // @JoinColumn(name = "source_stage_template_id", nullable = true)
    // private CompetitionStageTemplateEntity sourceStageTemplate;

    @ManyToOne
    @JoinColumn(name = "source_event_template_id", nullable = true)
    private CompetitionEventTemplateEntity sourceEventTemplate;

    @Column(name = "source_position")
    private Integer sourcePosition;

    @Column(name = "seed")
    private Integer seed;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

}