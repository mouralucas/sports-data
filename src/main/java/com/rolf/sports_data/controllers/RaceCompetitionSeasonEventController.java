package com.rolf.sports_data.controllers;

import java.util.List;

import com.rolf.sports_data.dto.RaceCompetitionSeasonEventSession.RaceCompetitionSeasonEventSessionResponseDto;
import com.rolf.sports_data.dto.RaceCompetitionSeasonEventSessionResult.RaceCompetitionSeasonEventSessionResultCreateRequest;
import com.rolf.sports_data.dto.RaceCompetitionSeasonEventSessionResult.RaceCompetitionSeasonEventSessionResultResponseDto;
import com.rolf.sports_data.dto.raceCompetitonSeasonEventEntry.RaceCompetitionSeasonEventEntryResponseDto;
import com.rolf.sports_data.mappers.RaceCompetitionSeasonEventSessionResultMapper;
import com.rolf.sports_data.services.RaceCompetitionSeasonEventEntryService;
import com.rolf.sports_data.services.RaceCompetitionSeasonEventSessionResultService;
import com.rolf.sports_data.services.RaceCompetitionSeasonEventSessionService;
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
@RequestMapping(ApiRoutes.API_V1 + "/season")
public class RaceCompetitionSeasonEventController {
    private final RaceCompetitionSeasonEventService raceCompetitionSeasonEventService;
    private final RaceCompetitionSeasonEventEntryService raceCompetitionSeasonEventEntryService;
    private final RaceCompetitionSeasonEventSessionService raceCompetitionSeasonEventSessionService;
    private final RaceCompetitionSeasonEventSessionResultService raceCompetitionSeasonEventSessionResultService;

    @Autowired
    public RaceCompetitionSeasonEventController(
            RaceCompetitionSeasonEventService raceCompetitionSeasonEventService,
            RaceCompetitionSeasonEventEntryService raceCompetitionSeasonEventEntryService,
            RaceCompetitionSeasonEventSessionService raceCompetitionSeasonEventSessionService,
            RaceCompetitionSeasonEventSessionResultService raceCompetitionSeasonEventSessionResultService
    ) {
        this.raceCompetitionSeasonEventService = raceCompetitionSeasonEventService;
        this.raceCompetitionSeasonEventEntryService = raceCompetitionSeasonEventEntryService;
        this.raceCompetitionSeasonEventSessionService = raceCompetitionSeasonEventSessionService;
        this.raceCompetitionSeasonEventSessionResultService = raceCompetitionSeasonEventSessionResultService;
    }

    // Events
    @GetMapping("/{seasonSlug}/events")
    public List<RaceCompetitionSeasonEventResponseDto> fetchAllEvents(@PathVariable String seasonSlug) {
        return raceCompetitionSeasonEventService.fetchAllSeasonEvents(seasonSlug);
    }

    @PostMapping("/{seasonSlug}/event")
    public RaceCompetitionSeasonEventResponseDto createSeasonEvent(@RequestBody RaceCompetitionSeasonEventRequest event, @PathVariable String seasonSlug) {
        System.out.print(seasonSlug);
        return raceCompetitionSeasonEventService.createSeasonEvent(event, seasonSlug);
    }

    //Event entries
    @GetMapping("/event/{eventId}/entries")
    public List<RaceCompetitionSeasonEventEntryResponseDto> getAllEntriesPerEvent() {
        return raceCompetitionSeasonEventEntryService.fetchAllCompetitionsEntryByEvent(1L);
    }

    // Event sessions
    @GetMapping("/event/{eventId}/sessions")
    public List<RaceCompetitionSeasonEventSessionResponseDto> findAllSessions(@PathVariable Long eventId) {
        return raceCompetitionSeasonEventSessionService.fetchAllSessionsByEvent(eventId);
    }

    // Event session results
    @PostMapping("/event/{eventId}/session/{sessionId}/result")
    public RaceCompetitionSeasonEventSessionResultResponseDto createResult(
            @RequestBody RaceCompetitionSeasonEventSessionResultCreateRequest result,
            @PathVariable Long eventId,
            @PathVariable Long sessionId
    ) {
        return raceCompetitionSeasonEventSessionResultService.createSessionResult(result);
    }
}
