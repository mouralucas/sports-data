package com.rolf.sports_data.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "team")
public class TeamEntity extends BaseEntity{
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "sport_id")
    private SportEntity sport;

    @Column(name = "name")
    private String name;

    @Column(name = "is_national_team")
    private boolean isNationalTeam = false;

    public TeamEntity(Long id) {
        super(id);
    }

    public TeamEntity() {
    }

    /* -------- Setters and Getters -------- */
}
