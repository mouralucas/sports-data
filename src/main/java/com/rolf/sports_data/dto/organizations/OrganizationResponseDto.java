package com.rolf.sports_data.dto.organizations;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class OrganizationResponseDto {
    
    public OrganizationResponseDto(@NotBlank Long id, @NotBlank @Size(min = 1, max = 150) String name,
            @Size(min = 1, max = 20) String acronym, String description, @NotBlank boolean active) {
        this.id = id;
        this.name = name;
        this.acronym = acronym;
        this.description = description;
        this.active = active;
    }

    @NotBlank
    private Long id;

    @NotBlank
    @Size(min = 1, max = 150)
    private String name;

    @Size(min = 1, max = 20)
    private String acronym;

    private String description;

    @NotBlank
    private boolean active;

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

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    

}
