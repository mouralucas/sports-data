package com.rolf.sports_data.enums;

public enum ParticipantTypeEnum {

    TEAM("team", "Time"),
    NATIONAL_TEAM("national_team", "Seleção Nacional"),
    PLAYER("player", "Jogador"),
    DRIVER("driver", "Piloto"),
    CAR("car", "Carro"),
    CONSTRUCTOR("constructor", "Construtor"),
    ATHLETE("athlete", "Atleta"),
    COMPETITOR("competitor", "Competidor"),
    OTHER("other", "Outro");

    private final String code;
    private final String description;

    ParticipantTypeEnum(String code, String description) {
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
