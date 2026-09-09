package com.rolf.sports_data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "organization",
    indexes = {
        @Index(name = "idx_organization_parent", columnList = "parent_organization_id"),
        @Index(name = "idx_organization_name", columnList = "name")
    }
)
public class OrganizationEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "parent_organization_id")
    private OrganizationEntity parentOrganization;

    @OneToMany(mappedBy = "organization")
    private List<RaceCompetitionEntity> competitions = new ArrayList<>();

    @OneToMany(mappedBy = "parentOrganization")
    private List<OrganizationEntity> childOrganizations = new ArrayList<>();

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "short_name", length = 50, nullable = true)
    private String shortName;

    @Column(name = "acronym", length = 20, nullable = true)
    private String acronym;

    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    private String description;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = true)
    private LocationEntity location;

    @Column(name = "active")
    private boolean active = true;
}
