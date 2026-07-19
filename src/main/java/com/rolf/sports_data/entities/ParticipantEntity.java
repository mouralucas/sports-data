package com.rolf.sports_data.entities;

import java.util.ArrayList;
import java.util.List;

import com.rolf.sports_data.enums.ParticipantTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "participant")
public class ParticipantEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "sport_id")
    private SportEntity sport;

    @Enumerated(EnumType.STRING)
    @Column(name = "participant_type", length = 50)
    private ParticipantTypeEnum participantType;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "short_name", length = 100, nullable = true)
    private String shortName;

    @Column(name = "acronym", length = 20, nullable = true)
    private String acronym;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationEntity countryLocation;

    @Column(name = "status")
    private boolean status;

    @OneToMany(mappedBy = "participant")
    private List<CompetitionEntryEntity> competitionEntries = new ArrayList<>();
}
