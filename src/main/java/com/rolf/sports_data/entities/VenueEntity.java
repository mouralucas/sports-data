package com.rolf.sports_data.entities;

import com.rolf.sports_data.enums.VenueTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

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

}
