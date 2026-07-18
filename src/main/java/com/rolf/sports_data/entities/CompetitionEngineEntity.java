package com.rolf.sports_data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "competition_engine",
    indexes = {@Index(name = "idx_competition_engine_code", columnList = "code")}
)
public class CompetitionEngineEntity extends BaseEntity{
    @Column(name = "code", length = 100)
    private String code;
    
    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "engine_version")
    private Integer engineVersion;

    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    private String description;

    @Column(name = "active")
    private boolean active = true;

    @OneToMany(mappedBy = "competitionEngine")
    private java.util.List<CompetitionEditionEntity> competitionEditions;
}
