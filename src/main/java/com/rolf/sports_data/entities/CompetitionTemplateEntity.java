package com.rolf.sports_data.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Index;

@Entity
@Table(name = "competition_template", indexes = {
        @Index(name = "idx_competition_template_competition", columnList = "competition_id") })
public class CompetitionTemplateEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "competition_id")
    private CompetitionEntity competition;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "version")
    private Integer version;

    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    private String description;

    @Column(name = "active")
    private boolean active = true;

    @OneToMany(mappedBy = "competitionTemplate")
    private List<CompetitionStageTemplateEntity> competitionStageTemplates = new ArrayList<>();

    @OneToMany(mappedBy = "competitionTemplate")
    private List<CompetitionRuleEntity> competitionRules = new ArrayList<>();
}
