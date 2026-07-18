package com.rolf.sports_data.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "competition_event_template", indexes = {
        @Index(name = "idx_event_template_stage", columnList = "competition_stage_template_id"),
        @Index(name = "idx_event_template_order", columnList = "event_order"),
})
public class CompetitionEventTemplateEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "competition_stage_template_id")
    private CompetitionStageTemplateEntity competitionStageTemplate;

    @Column(name = "event_order")
    private Integer eventOrder;

    @Column(name = "name", length = 200, nullable = true)
    private String name;

    @Column(name = "event_type", length = 40)
    private String eventType;

    @ManyToOne
    @JoinColumn(name = "home_slot_template_id", nullable = true)
    private StageSlotTemplateEntity homeSlotTemplateId;

    @ManyToOne
    @JoinColumn(name = "away_slot_template_id", nullable = true)
    private StageSlotTemplateEntity awaySlotTemplateId;

    @ManyToOne
    @JoinColumn(name = "venue_id", nullable = true)
    private VenueEntity venueId;

    @Column(name = "configuration", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> configuration;

    @OneToMany(mappedBy = "competitionEventTemplate")
    private List<CompetitionEventEntity> competitionEvents = new ArrayList<>();

}
