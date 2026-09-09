package com.rolf.sports_data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(
    name = "race_competition",
    indexes ={
        @Index(name = "idx_race_competition_name", columnList = "name"),
        @Index(name = "idx_race_competition_organization", columnList = "organization_id")
    }
)
public class RaceCompetitionEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private OrganizationEntity organization;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "short_name", length = 100)
    private String short_name;

    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    private String description;

    @Column(name = "active")
    private boolean active = true;
}