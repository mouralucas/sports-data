package com.rolf.sports_data.entities;

import com.rolf.sports_data.enums.ParticipantTypeEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "participant")
public class ParticipantEntity extends BaseEntity {

    
    public ParticipantEntity(ParticipantTypeEnum participantType, String name, String shortName,
            String acronym, LocationEntity countryLocation, boolean status) {
        this.participantType = participantType;
        this.name = name;
        this.shortName = shortName;
        this.acronym = acronym;
    }

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
    private boolean status = true;

    public SportEntity getSport() {
        return sport;
    }

    public void setSport(SportEntity sport) {
        this.sport = sport;
    }

    public ParticipantTypeEnum getParticipantType() {
        return participantType;
    }

    public void setParticipantType(ParticipantTypeEnum participantType) {
        this.participantType = participantType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public LocationEntity getCountryLocation() {
        return countryLocation;
    }

    public void setCountryLocation(LocationEntity countryLocation) {
        this.countryLocation = countryLocation;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    // @OneToMany(mappedBy = "participant")
    // private List<CompetitionEntryEntity> competitionEntries = new ArrayList<>();

    
}
