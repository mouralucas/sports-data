package com.rolf.sports_data.entities;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "race_competition_season_event")
public class RaceCompetitionSeasonEventEntity extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "race_competition_season_id")
    private RaceCompetitionSeasonEntity competitionSeason;

    public RaceCompetitionSeasonEventEntity() {
    }

    public RaceCompetitionSeasonEventEntity(String name, LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public RaceCompetitionSeasonEventEntity(Long id, String name, LocalDateTime startDate, LocalDateTime endDate,
            RaceCompetitionSeasonEntity competitionSeason) {
        super(id);
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.competitionSeason = competitionSeason;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public RaceCompetitionSeasonEntity getCompetitionSeason() {
        return competitionSeason;
    }

    public void setCompetitionSeason(RaceCompetitionSeasonEntity competitionSeason) {
        this.competitionSeason = competitionSeason;
    }
}
