package com.rolf.sports_data.enums;

public enum CarClassEnum {
    FORMULA("FORMULA", "Formula"),
    GT3("GT3", "GT3"),
    HYPERCAR("HYPERCAR", "Hypercar"),
    LMP2("LMP2", "LMP2"),
    GT4("GT4", "GT4");

    private final String code;
    private final String description;

    CarClassEnum(String code, String description) {
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
