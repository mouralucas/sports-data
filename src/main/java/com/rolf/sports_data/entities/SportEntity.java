package com.rolf.sports_data.entities;

import jakarta.persistence.*;

@Entity
@Table(
    name = "sport",
     indexes = {
        @Index(name = "idx_sport_name", columnList = "name"),
        @Index(name = "idx_sport_slug", columnList = "slug")
    }
)
public class SportEntity extends BaseEntity {

    @Column(name = "name", length = 120)
    private String name;

    @Column(name = "slug", length = 120)
    private String slug;

    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    private String description;

    @Column(name = "active")
    private boolean active = true;

    public SportEntity() {
    }

    public SportEntity(Long id){
        super(id);
    }

    /* -------- Setters and Getters -------- */
}
