package com.rolf.sports_data.entities;

import java.time.LocalDate;

import com.rolf.sports_data.enums.ParticipantMembershipTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "participant_membership",
    indexes = {
        @Index(name = "idx_participant_membership_parent_participant", columnList = "source_participant_id"),
        @Index(name = "idx_participant_membership_child_participant", columnList = "child_participant_id"),
        @Index(name = "idx_participant_membership_membership_type", columnList = "relationType")
    }
)
public class ParticipantMembershipEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "parent_participant_id")
    private ParticipantEntity parentParticipant;

    @ManyToOne
    @JoinColumn(name = "child_participant_id")
    private ParticipantEntity childParticipant;

    @Enumerated(EnumType.STRING)
    @Column(name = "membership_type", length = 50)
    private ParticipantMembershipTypeEnum membershipType;

    @Column(name = "valid_from")
    private LocalDate validFrom;

    @Column(name = "valid_until")
    private LocalDate validUntil;

}
