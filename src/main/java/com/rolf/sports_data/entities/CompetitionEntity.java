package com.rolf.sports_data.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Index;

@Entity
@Table(
    name = "competition",
    indexes ={
        @Index(name = "idx_competition_name", columnList = "name"),
        @Index(name = "idx_competition_sport", columnList = "sport_id"),
        @Index(name = "idx_competition_organization", columnList = "organization_id")
    }
)
public class CompetitionEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private OrganizationEntity organization;

    @ManyToOne
    @JoinColumn(name = "sport_id")
    private SportEntity sport;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "short_name", length = 100)
    private String short_name;

    @Column(name = "slug", length = 200)
    private String slug;

    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    private String description;

    @Column(name = "active")
    private boolean active = true;

    @OneToMany(mappedBy = "competition")
    private List<CompetitionTemplateEntity> competitionTemplates = new ArrayList<>();

    @OneToMany(mappedBy = "competition")
    private List<CompetitionEditionEntity> competitionEditions = new ArrayList<>();
}