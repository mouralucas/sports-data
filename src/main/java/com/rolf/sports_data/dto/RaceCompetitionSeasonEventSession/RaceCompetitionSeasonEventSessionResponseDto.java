package com.rolf.sports_data.dto.RaceCompetitionSeasonEventSession;

import java.time.LocalDateTime;

public class RaceCompetitionSeasonEventSessionResponseDto {
    private Long eventId;
    private String eventName;
    private Long id;
    private String name;
    private String sessionType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public RaceCompetitionSeasonEventSessionResponseDto() {
    }

    public RaceCompetitionSeasonEventSessionResponseDto(Long eventId, String eventName, Long id, String name, String sessionType, LocalDateTime startDate, LocalDateTime endDate) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.id = id;
        this.name = name;
        this.sessionType = sessionType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
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
