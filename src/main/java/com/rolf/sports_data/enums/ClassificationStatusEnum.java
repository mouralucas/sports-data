package com.rolf.sports_data.enums;

public enum ClassificationStatusEnum {

    PENDING("pending", "Pending"),
    ACTIVE("active", "Active"),
    CALCULATED("calculated", "Calculated"),
    INVALID("invalid", "Invalid");

    private final String code;
    private final String description;

    ClassificationStatusEnum(String code, String description) {
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
