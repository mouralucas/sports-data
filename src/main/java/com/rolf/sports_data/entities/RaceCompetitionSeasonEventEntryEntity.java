package com.rolf.sports_data.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


/**
 * RaceCompetitionSeasonEventEntryEntity
 * 
 * A entry should be for every lower granularity, that means the pilot.
 * If a constructor have more than one pilot will appear more than once. The same
 *  apply to a car, if more than one pilot the car will appear more than once
 */
@Entity
@Table(name = "race_competition_season_event_entry")
public class RaceCompetitionSeasonEventEntryEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competition_season_event_id", nullable = false)
    private RaceCompetitionSeasonEventEntity event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participant_id", nullable = false)
    private ParticipantEntity participant;

    // Contructor already present on car, but duplicated here to simplify queries
    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "constructor_id")
    private ConstructorEntity constructor;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "car_id")
    private RaceCarEntity car;

    public RaceCompetitionSeasonEventEntity getEvent() {
        return event;
    }

    public void setEvent(RaceCompetitionSeasonEventEntity event) {
        this.event = event;
    }

    public ParticipantEntity getParticipant() {
        return participant;
    }

    public void setParticipant(ParticipantEntity participant) {
        this.participant = participant;
    }

    public ConstructorEntity getConstructor() {
        return constructor;
    }

    public void setConstructor(ConstructorEntity constructor) {
        this.constructor = constructor;
    }

    public RaceCarEntity getCar() {
        return car;
    }

    public void setCar(RaceCarEntity car) {
        this.car = car;
    }
}
