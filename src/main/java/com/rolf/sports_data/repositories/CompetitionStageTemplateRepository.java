package com.rolf.sports_data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rolf.sports_data.entities.CompetitionStageTemplateEntity;

public interface CompetitionStageTemplateRepository extends JpaRepository<CompetitionStageTemplateEntity, Long> {
}