package com.rolf.sports_data.entities;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "classification_entry")
public class ClassificationEntryEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "classification_id")
    private ClassificationEntity classification;

    @ManyToOne
    @JoinColumn(name = "competition_entry_id")
    private CompetitionEntryEntity competitionEntry;

    @Column(name = "position")
    private Integer position;

    @Column(name = "points", nullable = false)
    private Double points;

    @Column(name = "wins", nullable = true)
    private Integer wins;

    @Column(name = "ties", nullable = true)
    private Integer ties;

    @Column(name = "losses", nullable = true)
    private Integer losses;

    @Column(name = "additional_data", columnDefinition = "jsonb", nullable = true)
    @JdbcTypeCode(SqlTypes.JSON)
    private String additionalData;

    @OneToMany(mappedBy = "classificationEntry")
    private List<ClassificationMetricEntity> classificationMetrics = new ArrayList<>();
}
