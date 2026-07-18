package com.rolf.sports_data.enums;

public enum CompetitionStatusEnum {

    DRAFT("draft", "Draft"),
    REGISTRATION_OPEN("registration_open", "Registration Open"),
    REGISTRATION_CLOSED("registration_closed", "Registration Closed"),
    ACTIVE("active", "Active"),
    COMPLETED("completed", "Completed"),
    CANCELLED("cancelled", "Cancelled");

    private final String code;
    private final String description;

    CompetitionStatusEnum(String code, String description) {
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
