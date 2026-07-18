package com.rolf.sports_data.enums;

public enum ClassificationTypeEnum {

    OVERALL("overall", "Overall"),
    GROUP("group", "Group"),
    PLAYOFF("playoff", "Playoff"),
    CUSTOM("custom", "Custom");

    private final String code;
    private final String description;

    ClassificationTypeEnum(String code, String description) {
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
