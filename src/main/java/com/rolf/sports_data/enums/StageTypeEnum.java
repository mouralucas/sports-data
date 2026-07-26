package com.rolf.sports_data.enums;

public enum StageTypeEnum {

    GROUP_STAGE("group", "Group"),
    LEAGUE_STAGE("league", "League"),
    KNOCKOUT_STAGE("knockout", "Knockout"),
    QUALIFICATION_STAGE("qualification", "Qualification"),
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
