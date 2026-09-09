package com.rolf.sports_data.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rolf.sports_data.dto.competitionStageTemplate.CompetitionStageTemaplateResponseDto;
import com.rolf.sports_data.entities.CompetitionStageTemplateEntity;
import com.rolf.sports_data.mappers.CompetitionStageTemaplateMapper;
import com.rolf.sports_data.repositories.CompetitionStageTemplateRepository;

@Service
public class CompetitionStageTemplateService {
    private CompetitionStageTemplateRepository competitionStageTemplateRepository;
    private CompetitionStageTemaplateMapper competitionStageTemaplateMapper;
    
    public CompetitionStageTemplateService(
        CompetitionStageTemplateRepository competitionStageTemplateRepository,
        CompetitionStageTemaplateMapper competitionStageTemaplateMapper
    ) {
        this.competitionStageTemplateRepository = competitionStageTemplateRepository;
        this.competitionStageTemaplateMapper = competitionStageTemaplateMapper;
    }

    public List<CompetitionStageTemaplateResponseDto> findAll() {
        List<CompetitionStageTemplateEntity> a = competitionStageTemplateRepository.findAll();

        return competitionStageTemaplateMapper.toListResponse(a);
    }
}
