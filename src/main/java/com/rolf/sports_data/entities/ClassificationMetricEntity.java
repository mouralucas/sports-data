package com.rolf.sports_data.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "classification_metric")
public class ClassificationMetricEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "classification_entry_id")
    private ClassificationEntryEntity classificationEntry;

    @Column(name = "metric_type", length = 50)
    private String metricType;

    @Column(name = "value", nullable = true)
    private Double value;

    @Column(name = "calculated_at", nullable = true)
    private LocalDateTime calculatedAt;
}
