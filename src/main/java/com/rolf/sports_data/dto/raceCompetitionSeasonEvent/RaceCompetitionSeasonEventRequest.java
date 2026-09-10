package com.rolf.sports_data.dto.raceCompetitionSeasonEvent;

import java.time.LocalDateTime;

public class RaceCompetitionSeasonEventRequest {
    private String name;
    private Long raceCompetitionSeasonId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    public RaceCompetitionSeasonEventRequest(String name, Long raceCompetitionSeasonId, LocalDateTime startDate,
            LocalDateTime endDate) {
        this.name = name;
        this.raceCompetitionSeasonId = raceCompetitionSeasonId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRaceCompetitionSeasonId() {
        return raceCompetitionSeasonId;
    }

    public void setRaceCompetitionSeasonId(Long raceCompetitionSeasonId) {
        this.raceCompetitionSeasonId = raceCompetitionSeasonId;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    
}
