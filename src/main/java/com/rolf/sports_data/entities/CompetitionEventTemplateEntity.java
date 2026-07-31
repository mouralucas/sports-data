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

    private CompetitionStageTemplateEntity competitionStageTemplate;

    private Integer eventOrder;

    private String name;

    private String description;

    // REMOVIDO
    // private StageSlotTemplateEntity homeSlotTemplate;

    // REMOVIDO
    // private StageSlotTemplateEntity awaySlotTemplate;

    private List<CompetitionEventParticipantTemplateEntity> participants;

}
