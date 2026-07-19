package com.rolf.sports_data.enums;

public enum LocationTypeEnum {
    COUNTRY("country", "País"),
    STATE("state", "Estado"),
    CITY("city", "Cidade"),
    REGION("region", "Região"),
    OTHER("other", "Outro");

    private final String code;
    private final String description;

    LocationTypeEnum(String code, String description) {
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
