package com.rolf.sports_data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity 
@Table(name = "constructor")
public class ConstructorEntity extends BaseEntity{
    @Column(name = "name", length = 200)
    private String name;

    @Column (name = "description", columnDefinition = "TEXT", nullable = true)
    private String description;

    @Column (name = "year", nullable = true)
    private Integer year;

    @Column (name = "country")
    private String country;
}
