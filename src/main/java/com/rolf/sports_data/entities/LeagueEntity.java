package com.rolf.sports_data.entities;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "league")
public class LeagueEntity extends BaseEntity {
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "sport_id")
    private SportEntity sport;

    @Column(name = "name")
    private String name;

    @Column(name = "description", nullable = true)
    private String description;

    /* If true the player also score points */
    @Column(name = "player_score")
    private boolean playerScore = false; 

    /* -------- Setters and Getters -------- */
    public SportEntity getSport() {
        return sport;
    }

    public void setSport(SportEntity sport) {
        this.sport = sport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPlayerScore() {
        return playerScore;
    }

    public void setPlayerScore(boolean playerScore) {
        this.playerScore = playerScore;
    }
}
