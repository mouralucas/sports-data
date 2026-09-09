package com.rolf.sports_data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
    name = "competition_season_event_entry_participant",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_event_entry_participant",
            columnNames = {
                "competition_season_event_entry_id",
                "participant_id"
            }
        )
    }
)
public class CompetitionSeasonEventEntryParticipantEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "competition_season_event_entry_id",
        nullable = false
    )
    private CompetitionSeasonEventEntryEntity entry;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
        name = "participant_id",
        nullable = false
    )
    private ParticipantEntity participant;

    @Column(name = "position")
    private Integer position;
}