package com.rolf.sports_data.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rolf.sports_data.api.ApiRoutes;
import com.rolf.sports_data.dto.raceCompetitionSeasonEvent.RaceCompetitionSeasonEventRequest;
import com.rolf.sports_data.dto.raceCompetitionSeasonEvent.RaceCompetitionSeasonEventResponseDto;
import com.rolf.sports_data.services.RaceCompetitionSeasonEventService;

@RestController
@RequestMapping(ApiRoutes.API_V1)
public class RaceCompetitionSeasonEventController {
    private RaceCompetitionSeasonEventService raceCompetitionSeasonEventService;

    @Autowired
    public RaceCompetitionSeasonEventController(RaceCompetitionSeasonEventService raceCompetitionSeasonEventService) {
        this.raceCompetitionSeasonEventService = raceCompetitionSeasonEventService;
    }

    @GetMapping("/season/{seasonSlug}/events")
    public List<RaceCompetitionSeasonEventResponseDto> fetchAllEvents(@PathVariable String seasonSlug) {
        return raceCompetitionSeasonEventService.fetchAllSeasonEvents(seasonSlug);
    }

    @PostMapping("/season/event")
    public RaceCompetitionSeasonEventResponseDto createSeasonEvent(@RequestBody RaceCompetitionSeasonEventRequest event) {
        return raceCompetitionSeasonEventService.createSeasonEvent(event);
    }
}
