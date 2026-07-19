package com.rolf.sports_data.entities;

import java.util.ArrayList;
import java.util.List;

import com.rolf.sports_data.enums.VenueTypeEnum;

import jakarta.persistence.*;


@Entity
@Table(name = "venue")
public class VenueEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "venue_type", length = 40)
    private VenueTypeEnum venueType;

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

     /* Setters and Getters */
    public LocationEntity getLocation() {
        return location;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public VenueTypeEnum getVenueType() {
        return venueType;
    }

    public void setVenueType(VenueTypeEnum venueType) {
        this.venueType = venueType;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Integer getLengthMeters() {
        return lengthMeters;
    }

    public void setLengthMeters(Integer lengthMeters) {
        this.lengthMeters = lengthMeters;
    }

    public Boolean isActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<CompetitionEventEntity> getCompetitionEvents() {
        return competitionEvents;
    }

    public void setCompetitionEvents(List<CompetitionEventEntity> competitionEvents) {
        this.competitionEvents = competitionEvents;
    }

    public List<CompetitionEventTemplateEntity> getCompetitionEventTemplates() {
        return competitionEventTemplates;
    }

    public void setCompetitionEventTemplates(List<CompetitionEventTemplateEntity> competitionEventTemplates) {
        this.competitionEventTemplates = competitionEventTemplates;
    }

    
}
