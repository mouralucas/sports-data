package com.rolf.sports_data.entities;

import java.time.LocalDateTime;
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
@Table(name = "competition_entry",
    indexes = {
        @Index(name = "idx_competition_entry_edition", columnList = "competition_edition_id"),
        @Index(name = "idx_competition_entry_participant", columnList = "participant_id"),
        @Index(name = "idx_competition_entry_status", columnList = "status")
    }
)
public class CompetitionEntryEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "competition_edition_id")
    private CompetitionEditionEntity competitionEdition;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private ParticipantEntity participant;

    @Column(name = "seed", nullable = true)
    private Integer seed;

    @Column(name = "registration_number", length = 50, nullable = true)
    private String registrationNumber;

    @Column(name = "status", length = 30)
    private String status;

    @Column(name = "registered_at", nullable = true)
    private LocalDateTime registeredAt;

    @OneToMany(mappedBy = "competitionEntry")
    private List<StageSlotEntity> stageSlots = new ArrayList<>();

    @OneToMany(mappedBy = "competitionEntry")
    private List<CompetitionEventParticipantEntity> competitionEventParticipants = new ArrayList<>();
}