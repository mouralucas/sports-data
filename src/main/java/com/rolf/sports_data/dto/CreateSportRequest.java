package com.rolf.sports_data.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateSportRequest {

    @NotBlank
    @Size(min = 1, max = 150)
    private String name;

    @Size(max = 200, message = "Description cannot exceed 200 characters")
    private String description;

    public @NotBlank @Size(min = 1, max = 150) String getName() {
        return name;
    }

    public void setName(@NotBlank @Size(min = 1, max = 150) String name) {
        this.name = name;
    }

    public @Size(max = 200, message = "Description cannot exceed 200 characters") String getDescription() {
        return description;
    }

    public void setDescription(@Size(max = 200, message = "Description cannot exceed 200 characters") String description) {
        this.description = description;
    }
}
