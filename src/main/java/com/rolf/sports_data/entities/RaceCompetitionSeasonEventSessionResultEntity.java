package com.rolf.sports_data.entities;

import java.math.BigDecimal;
import java.sql.Time;
import java.time.Duration;

import com.rolf.sports_data.services.RaceCompetitionSeasonEventEntryService;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.rolf.sports_data.enums.SessionResultStatusEnum;

@Entity
@Table(name = "race_competition_season_event_session_result", uniqueConstraints = {
        @UniqueConstraint(name = "uk_session_result_entry", columnNames = {
                "race_competition_season_event_session_id",
                "race_competition_season_event_entry_id"
        })
})
public class RaceCompetitionSeasonEventSessionResultEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "session_id", nullable = true)
    private RaceCompetitionSeasonEventSessionEntity session;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entry_id", nullable = true)
    private RaceCompetitionSeasonEventEntryEntity entry;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 30)
    private SessionResultStatusEnum status;

    // Time in milliseconds
    @Column(name = "time")
    private Long time;

    // Total laps in case of RACE session
    @Column(name="laps", nullable = true)
    private Integer laps;

    public RaceCompetitionSeasonEventSessionResultEntity() {}

    public RaceCompetitionSeasonEventSessionResultEntity(SessionResultStatusEnum status, Long time, Integer laps) {
        this.status = status;
        this.time = time;
        this.laps = laps;
    }

    public RaceCompetitionSeasonEventSessionResultEntity(RaceCompetitionSeasonEventSessionEntity session, RaceCompetitionSeasonEventEntryEntity entry, SessionResultStatusEnum status, Long time, Integer laps) {
        this.session = session;
        this.entry = entry;
        this.status = status;
        this.time = time;
        this.laps = laps;
    }

    public RaceCompetitionSeasonEventSessionResultEntity(Long id, RaceCompetitionSeasonEventSessionEntity session, RaceCompetitionSeasonEventEntryEntity entry, SessionResultStatusEnum status, Long time, Integer laps) {
        super(id);
        this.session = session;
        this.entry = entry;
        this.status = status;
        this.time = time;
        this.laps = laps;
    }

    public RaceCompetitionSeasonEventSessionEntity getSession() {
        return session;
    }

    public void setSession(RaceCompetitionSeasonEventSessionEntity session) {
        this.session = session;
    }

    public RaceCompetitionSeasonEventEntryEntity getEntry() {
        return entry;
    }

    public void setEntry(RaceCompetitionSeasonEventEntryEntity entry) {
        this.entry = entry;
    }

    public SessionResultStatusEnum getStatus() {
        return status;
    }

    public void setStatus(SessionResultStatusEnum status) {
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