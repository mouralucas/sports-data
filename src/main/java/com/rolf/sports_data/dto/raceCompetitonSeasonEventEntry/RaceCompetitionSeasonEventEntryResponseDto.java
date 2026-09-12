package com.rolf.sports_data.dto.raceCompetitonSeasonEventEntry;

public class RaceCompetitionSeasonEventEntryResponseDto {
    private Long id;
    private Long event_id;
    private String event_name;
    private Long participant_id;
    private String participant_name;
    private Long constructor_id;
    private String constructor__name;
    private Long car_id;
    private String car_number;

    public RaceCompetitionSeasonEventEntryResponseDto(Long id, Long event_id, String event_name, Long participant_id, String participant_name, Long constructor_id, String constructor__name, Long car_id, String car_number) {
        this.id = id;
        this.event_id = event_id;
        this.event_name = event_name;
        this.participant_id = participant_id;
        this.participant_name = participant_name;
        this.constructor_id = constructor_id;
        this.constructor__name = constructor__name;
        this.car_id = car_id;
        this.car_number = car_number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Long event_id) {
        this.event_id = event_id;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public Long getParticipant_id() {
        return participant_id;
    }

    public void setParticipant_id(Long participant_id) {
        this.participant_id = participant_id;
    }

    public String getParticipant_name() {
        return participant_name;
    }

    public void setParticipant_name(String participant_name) {
        this.participant_name = participant_name;
    }

    public Long getConstructor_id() {
        return constructor_id;
    }

    public void setConstructor_id(Long constructor_id) {
        this.constructor_id = constructor_id;
    }

    public String getConstructor__name() {
        return constructor__name;
    }

    public void setConstructor__name(String constructor__name) {
        this.constructor__name = constructor__name;
    }

    public Long getCar_id() {
        return car_id;
    }

    public void setCar_id(Long car_id) {
        this.car_id = car_id;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }
}
