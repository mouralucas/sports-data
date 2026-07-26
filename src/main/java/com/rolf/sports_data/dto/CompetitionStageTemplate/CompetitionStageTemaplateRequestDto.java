package com.rolf.sports_data.dto.CompetitionStageTemplate;

import jakarta.validation.constraints.NotNull;

public class CompetitionStageTemaplateRequestDto {
    @NotNull
    private Long competitionTemplateId;

    public Long getCompetitionTemplateId() {
        return competitionTemplateId;
    }

    public void setCompetitionTemplateId(Long competitionTemplateId) {
        this.competitionTemplateId = competitionTemplateId;
    }

    
}
