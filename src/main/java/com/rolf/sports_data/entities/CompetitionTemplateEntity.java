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

    @Column(name = "template_version")
    private Integer template_version;

    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    private String description;

    @Column(name = "active")
    private boolean active = true;

    @OneToMany(mappedBy = "competitionTemplate")
    private List<CompetitionStageTemplateEntity> competitionStageTemplates = new ArrayList<>();

    @OneToMany(mappedBy = "competitionTemplate")
    private List<CompetitionTemplateRuleEntity> competitionRules = new ArrayList<>();

    /* Setters and Getters */
    public CompetitionEntity getCompetition() {
        return competition;
    }

    public void setCompetition(CompetitionEntity competition) {
        this.competition = competition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTemplateVersion() {
        return template_version;
    }

    public void setTemplateVersion(Integer version) {
        this.template_version = version;
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

    public List<CompetitionStageTemplateEntity> getCompetitionStageTemplates() {
        return competitionStageTemplates;
    }

    public void setCompetitionStageTemplates(List<CompetitionStageTemplateEntity> competitionStageTemplates) {
        this.competitionStageTemplates = competitionStageTemplates;
    }

    public List<CompetitionTemplateRuleEntity> getCompetitionRules() {
        return competitionRules;
    }

    public void setCompetitionRules(List<CompetitionTemplateRuleEntity> competitionRules) {
        this.competitionRules = competitionRules;
    }

}
