package com.rolf.sports_data.dto.participants;

import com.rolf.sports_data.enums.ParticipantTypeEnum;

public class ParticipantResponseDto {
    private String name;
    private String acronym;
    private String shortName;
    private ParticipantTypeEnum participantType;
    private Long sportId;
    private String sportName;
    private boolean status;

    public ParticipantResponseDto(String name, String acronym, String shortName, ParticipantTypeEnum participantType,
            boolean status) {
        this.name = name;
        this.acronym = acronym;
        this.shortName = shortName;
        this.participantType = participantType;
        // this.sportId = sportId;
        // this.sportName = sportName;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public ParticipantTypeEnum getParticipantType() {
        return participantType;
    }

    public void setParticipantType(ParticipantTypeEnum participantType) {
        this.participantType = participantType;
    }

    public Long getSportId() {
        return sportId;
    }

    public void setSportId(Long sportId) {
        this.sportId = sportId;
    }

    public String getSportName() {
        return sportName;
    }

    public void setSportName(String sportName) {
        this.sportName = sportName;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
