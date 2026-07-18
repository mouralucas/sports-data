package com.rolf.sports_data.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class ParticipantRelationEntity extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "source_participant_id")
    private Long sourceParticipantId;

    @ManyToOne
    @JoinColumn(name = "target_participant_id")
    private Long targetParticipantId;

    @Column(name = "relation_type", length = 50)
    private String relationType;

    @Column(name = "valid_from")
    private LocalDate validFrom;
    
    @Column(name = "valid_until")
    private LocalDate validUntil;
    
}
