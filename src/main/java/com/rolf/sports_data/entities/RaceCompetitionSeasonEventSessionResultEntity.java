package com.rolf.sports_data.entities;

import java.math.BigDecimal;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.rolf.sports_data.enums.SessionResultStatusEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "race_competition_season_event_session_result", uniqueConstraints = {
        @UniqueConstraint(name = "uk_session_result_entry", columnNames = {
                "race_competition_season_event_session_id",
                "race_competition_season_event_entry_id"
        })
})
public class RaceCompetitionSeasonEventSessionResultEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "race_competition_season_event_session_id", nullable = false)
    private RaceCompetitionSeasonEventSessionEntity session;

    @Column(name = "position")
    private Integer position;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30)
    private SessionResultStatusEnum status;

    @Column(name = "value")
    private BigDecimal value;

    @Column(name = "result_data", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String result_data;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}