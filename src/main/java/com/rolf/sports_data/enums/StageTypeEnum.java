package com.rolf.sports_data.enums;

public enum StageTypeEnum {

    GROUP("group", "Group"),
    LEAGUE("league", "League"),
    ROUND_ROBIN("round_robin", "Round Robin"),
    KNOCKOUT("knockout", "Knockout"),
    QUALIFICATION("qualification", "Qualification"),
    CUSTOM("custom", "Custom");

    private final String code;
    private final String description;

    StageTypeEnum(String code, String description) {
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
