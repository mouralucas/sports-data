package com.rolf.sports_data.entities;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "league_season")
public class LeagueSeasonEntity extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name; // Usually the year (2024 or 2024/2025)

    /* Indicates which league this season belongs to */
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "league_id", nullable = false)
    private LeagueEntity league;

    /* Indicates if the league are divided by groups */
    @Column(name = "is_group", nullable = false)
    private boolean isGrouped; 

    /* Indicates if the season have playoffs */
    @Column(name = "is_playoff", nullable = false)
    private boolean isPlayoff;

    /* The teams for this season in the league */
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "season_team", joinColumns = @JoinColumn(name = "league_season_id"), inverseJoinColumns = @JoinColumn(name = "team_id"))
    @JsonManagedReference
    private List<TeamEntity> teams;
}