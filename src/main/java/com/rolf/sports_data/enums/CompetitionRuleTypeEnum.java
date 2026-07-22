package com.rolf.sports_data.enums;

public enum CompetitionRuleTypeEnum {

    ALLOW_DRAW("aloow_draw", "Permitir empate"),
    ALLOW_EXTRA_TIME("allow_extra_time", "Permitir tempo extra"),
    ALLOW_PENALTY_SHOOTOUT("allow_penalty_shootout", "Permitir cobrança de pênaltis"),
    POINTS_SYSTEM("points_system", "Sistema de pontos"),
    QUALIFICATION("qualification", "Qualificação"),
    ADVANCEMENT("advancement", "Avanço"),
    CLASSIFICATION("classification", "Classificação"),
    ENTRY_VALIDATION("entry_validation", "Validação de inscrição"),
    SCHEDULING("scheduling", "Agendamento");

    private final String code;
    private final String description;

    CompetitionRuleTypeEnum(String code, String description) {
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