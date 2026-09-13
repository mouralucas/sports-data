package com.rolf.sports_data.enums;

public enum SessionResultStatusEnum {
    DNF("DNF", "Do not finish"),
    DNS("DNS", "Do not start"),
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
