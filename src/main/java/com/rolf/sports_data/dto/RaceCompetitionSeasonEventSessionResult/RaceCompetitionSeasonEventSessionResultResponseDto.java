package com.rolf.sports_data.dto.RaceCompetitionSeasonEventSessionResult;

public class RaceCompetitionSeasonEventSessionResultResponseDto {
    private Long sessionId;
    private String sessionName;
    private Long entryId;
    private Long participantId;
    private String participantName;
    private String status;
    private Long time;
    private Integer laps;

    public RaceCompetitionSeasonEventSessionResultResponseDto(
            Long sessionId,
            String sessionName,
            Long entryId,
            Long participantId,
            String participantName,
            String status,
            Long time,
            Integer laps
    ) {
        this.sessionId = sessionId;
        this.sessionName = sessionName;
        this.entryId = entryId;
        this.participantId = participantId;
        this.participantName = participantName;
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

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public Long getEntryId() {
        return entryId;
    }

    public void setEntryId(Long entryId) {
        this.entryId = entryId;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getLaps() {
        return laps;
    }

    public void setLaps(Integer laps) {
        this.laps = laps;
    }
}
