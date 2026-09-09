package com.rolf.sports_data.enums;

/**
 * SessionTypeEnum
 */
public enum SessionTypeEnum {
    RACE("race", "Corrida"),
    MATCH("match", "Partida"),
    QUALIFYING("qualifying", "Qualificação"),
    FREE_PRACTICE("free_practice", "Treino livre");

    private final String code;
    private final String description;

    SessionTypeEnum(String code, String description) {
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
