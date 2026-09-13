package com.rolf.sports_data.dto.RaceCompetitionSeasonEventSessionResult;

import com.rolf.sports_data.enums.SessionResultStatusEnum;

public class RaceCompetitionSeasonEventSessionResultCreateRequest {
    private Long sessionId;
    private Long entryId;
    private SessionResultStatusEnum status;
    private String time;
    private Integer laps;

    public RaceCompetitionSeasonEventSessionResultCreateRequest(Long sessionId, Long entryId, SessionResultStatusEnum status, String time, Integer laps) {
        this.sessionId = sessionId;
        this.entryId = entryId;
        this.status = status;
        this.time = time;
        this.laps = laps;
    }

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public SessionResultStatusEnum getStatus() {
        return status;
    }

    public void setStatus(SessionResultStatusEnum status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getLaps() {
        return laps;
    }

    public void setLaps(Integer laps) {
        this.laps = laps;
    }
}
