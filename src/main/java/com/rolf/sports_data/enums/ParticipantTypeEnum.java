package com.rolf.sports_data.enums;

public enum ParticipantTypeEnum {

    TEAM("team", "Team"),
    PLAYER("player", "Player"),
    DRIVER("driver", "Driver"),
    ATHLETE("athlete", "Athlete"),
    COMPETITOR("competitor", "Competitor"),
    OTHER("other", "Other");

    private final String code;
    private final String description;

    ParticipantTypeEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
