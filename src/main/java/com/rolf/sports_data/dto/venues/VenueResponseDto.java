package com.rolf.sports_data.dto.venues;

import jakarta.validation.constraints.NotBlank;

public class VenueResponseDto {
    

    public VenueResponseDto(@NotBlank Long id, @NotBlank String name, @NotBlank String venueType, Integer capacity,
            Integer lengthMeters, @NotBlank Boolean active) {
        this.id = id;
        this.name = name;
        this.venueType = venueType;
        this.capacity = capacity;
        this.lengthMeters = lengthMeters;
        this.active = active;
    }

    @NotBlank
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    private String venueType;

    private Integer capacity;

    private Integer lengthMeters;

    @NotBlank
    private Boolean active;

    /* Setters and Getters */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVenueType() {
        return venueType;
    }

    public void setVenueType(String venueType) {
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
}
