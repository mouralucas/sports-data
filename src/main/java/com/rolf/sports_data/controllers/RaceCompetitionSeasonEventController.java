package com.rolf.sports_data.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rolf.sports_data.api.ApiRoutes;
import com.rolf.sports_data.dto.CompetitionSeasonEvent.RaceCompetitionSeasonEventResponseDto;
import com.rolf.sports_data.services.RaceCompetitionSeasonEventService;

@RestController
@RequestMapping(ApiRoutes.API_V1)
public class RaceCompetitionSeasonEventController {
    private RaceCompetitionSeasonEventService raceCompetitionSeasonEventService;

    @Autowired
    public RaceCompetitionSeasonEventController(RaceCompetitionSeasonEventService raceCompetitionSeasonEventService) {
        this.raceCompetitionSeasonEventService = raceCompetitionSeasonEventService;
    }

    // TODO: add a slug to season so the seasonId is more readable, created automatically
    // /season/f12026/events
    // /season/fe25-26/events
    @GetMapping("/season/{seasonId}/events")
    public List<RaceCompetitionSeasonEventResponseDto> fetchAllEvents(@PathVariable String seasonId) {
        return raceCompetitionSeasonEventService.fetchAllSeasonEvents(seasonId);
    }
}
