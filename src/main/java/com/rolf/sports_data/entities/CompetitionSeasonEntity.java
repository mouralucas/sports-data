package com.rolf.sports_data.entities;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "competition_season")
public class CompetitionSeasonEntity extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name; // Usually the year (2024 or 2024/2025)

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "competition_id", nullable = false)
    private CompetitionEntity competition;

    @Column(name = "start_date", nullable = true)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = true)
    private LocalDateTime endDate;

    @Column(name = "description", columnDefinition = "TEXT", nullable = true)
    private String description;
}