package com.rolf.sports_data.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "competition_edition", indexes = {
        @Index(name = "idx_competition_edition_competition", columnList = "competition_id"),
        @Index(name = "idx_competition_edition_season", columnList = "season"),
        @Index(name = "idx_competition_edition_status", columnList = "status")
})
public class CompetitionEditionEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "competition_id")
    private CompetitionEntity competition;

    @ManyToOne
    @JoinColumn(name = "competition_template_id")
    private CompetitionTemplateEntity competitionTemplate;

    @ManyToOne
    @JoinColumn(name = "competition_engine_id")
    private CompetitionEngineEntity competitionEngine;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "season", length = 50)
    private String season;

    @Column(name = "status", length = 30)
    private String status;

    @Column(name = "start_date", nullable = true)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = true)
    private LocalDate endDate;

    @Column(name = "registration_start")
    private LocalDate registrationStart;

    @Column(name = "registration_end")
    private LocalDate registrationEnd;

    @OneToMany(mappedBy = "competitionEdition")
    private List<CompetitionStageEntity> competitionStages = new ArrayList<>();

    @OneToMany(mappedBy = "competitionEdition")
    private List<CompetitionEntryEntity> competitionEntries = new ArrayList<>();

    @OneToMany(mappedBy = "competitionEdition")
    private List<CompetitionEventEntity> competitionEvents = new ArrayList<>();

    @OneToMany(mappedBy = "competitionEdition")
    private List<ClassificationEntity> classifications = new ArrayList<>();
}
