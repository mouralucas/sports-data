package com.rolf.sports_data.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "classification")
public class ClassificationEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "competition_edition_id")
    private CompetitionEditionEntity competitionEdition;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "classification_type", length = 50)
    private String classificationType; // enum

    @Column(name = "status", length = 30)
    private String status; // enum

    @Column(name = "calculated_at")
    private LocalDateTime calculatedAt;

    @OneToMany(mappedBy = "classification")
    private List<ClassificationEntryEntity> classificationEntries = new ArrayList<>();
}
