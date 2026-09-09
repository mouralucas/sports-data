package com.rolf.sports_data.enums;

public enum SessionResultStatusEnum {
    POINTS("POINTS", "Points"),
    TIME_TOTAL("DNF", "Do not finish"),
    TIME_PARTIAL("DNS", "Do not start"),
    TIME_UNKNOWN("UNKNOWN", "Unknown"),
    FINISHED("FINISHED", "Finished");

    private final String code;
    private final String description;

    SessionResultStatusEnum(String code, String description) {
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
