package com.rolf.sports_data.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rolf.sports_data.api.ApiRoutes;
import com.rolf.sports_data.dto.competitionStageTemplate.CompetitionStageTemaplateRequestDto;
import com.rolf.sports_data.dto.competitionStageTemplate.CompetitionStageTemaplateResponseDto;
import com.rolf.sports_data.services.CompetitionStageTemplateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiRoutes.API_V1)
public class CompetitionStageTemplateController {
    CompetitionStageTemplateService competitionStageTemplateService;

    public CompetitionStageTemplateController(CompetitionStageTemplateService competitionStageTemplateService) {
        this.competitionStageTemplateService = competitionStageTemplateService;
    }

    // TODO: param are not being enforced
    @GetMapping("/competition-stage-templates")
    public List<CompetitionStageTemaplateResponseDto> findCompetitionStageTemplates(
            @Valid @ModelAttribute CompetitionStageTemaplateRequestDto params) {
        return competitionStageTemplateService.findAll();
    }
}