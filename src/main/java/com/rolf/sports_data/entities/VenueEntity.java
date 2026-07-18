package com.rolf.sports_data.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;


@Entity
@Table(name = "venue")
public class VenueEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    @Column(name = "name")
    private String name;

    @Column(name = "venue_type", length = 40)
    private String venueType;

    @Column(name = "capacity", nullable = true)
    private Integer capacity;

    @Column(name = "length_meters", nullable = true)
    private Integer lengthMeters;

    @Column(name = "active")
    private Boolean active = true;

    @OneToMany(mappedBy = "venue")
    private List<CompetitionEventEntity> competitionEvents = new ArrayList<>();

    @OneToMany(mappedBy = "venueId")
    private List<CompetitionEventTemplateEntity> competitionEventTemplates = new ArrayList<>();
}
