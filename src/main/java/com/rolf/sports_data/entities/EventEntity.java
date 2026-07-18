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
@Table(name = "event")
public class EventEntity extends BaseEntity {
    @Column(name = "name")
    private String name;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "league_id")
    private LeagueEntity league;

    @Column(name = "date")
    private LocalDateTime date;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "league_season_id")
    private LeagueSeasonEntity leagueSeason;

    
    
    /* -------- Setters and Getters -------- */

    public LeagueSeasonEntity getLeagueSeason() {
        return leagueSeason;
    }

    public void setLeagueSeason(LeagueSeasonEntity leagueSeason) {
        this.leagueSeason = leagueSeason;
    }

}
