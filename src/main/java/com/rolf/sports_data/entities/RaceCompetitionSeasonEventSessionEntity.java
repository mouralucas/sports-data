package com.rolf.sports_data.entities;

import com.rolf.sports_data.enums.RaceSessionTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "race_competition_season_event_session")
public class RaceCompetitionSeasonEventSessionEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "race_competition_season_event_id", nullable = false)
    private RaceCompetitionSeasonEventEntity event;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "session_type", nullable = true)
    private RaceSessionTypeEnum type;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    public RaceCompetitionSeasonEventSessionEntity() {}

    public RaceCompetitionSeasonEventSessionEntity(RaceCompetitionSeasonEventEntity event, String name, RaceSessionTypeEnum type, LocalDateTime startDate, LocalDateTime endDate) {
        this.event = event;
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public RaceCompetitionSeasonEventSessionEntity(Long id, RaceCompetitionSeasonEventEntity event, String name, RaceSessionTypeEnum type, LocalDateTime startDate, LocalDateTime endDate) {
        super(id);
        this.event = event;
        this.name = name;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public RaceCompetitionSeasonEventEntity getEvent() {
        return event;
    }

    public void setEvent(RaceCompetitionSeasonEventEntity event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RaceSessionTypeEnum getType() {
        return type;
    }

    public void setType(RaceSessionTypeEnum type) {
        this.type = type;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}

