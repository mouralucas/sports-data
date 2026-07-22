package com.rolf.sports_data.enums;

public enum ParticipantMembershipTypeEnum {

    PLAYER("player", "Jogador"),
    DRIVER("driver", "Piloto"),
    MEMBER("member", "Membro"),
    COACH("coach", "Técnico"),
    MANAGER("manager", "Gerente"),
    CAPTAIN("captain", "Capitão"),
    RESERVE("reserve", "Reserva");

    private final String code;
    private final String description;

    ParticipantMembershipTypeEnum(String code, String description) {
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