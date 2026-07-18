package com.rolf.sports_data.enums;

public enum VenueTypeEnum {

    STADIUM("stadium", "Stadium"),
    CIRCUIT("circuit", "Circuit"),
    ARENA("arena", "Arena"),
    GYMNASIUM("gymnasium", "Gymnasium"),
    COURT("court", "Court"),
    TRACK("track", "Track"),
    OTHER("other", "Other");

    private final String code;
    private final String description;

    VenueTypeEnum(String code, String description) {
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