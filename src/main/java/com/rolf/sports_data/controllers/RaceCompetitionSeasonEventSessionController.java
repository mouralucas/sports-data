package com.rolf.sports_data.controllers;

import com.rolf.sports_data.api.ApiRoutes;
import com.rolf.sports_data.dto.RaceCompetitionSeasonEventSession.RaceCompetitionSeasonEventSessionResponseDto;
import com.rolf.sports_data.services.RaceCompetitionSeasonEventSessionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiRoutes.API_V1)
public class RaceCompetitionSeasonEventSessionController {
    private final RaceCompetitionSeasonEventSessionService raceCompetitionSeasonEventSessionService;

    public RaceCompetitionSeasonEventSessionController(RaceCompetitionSeasonEventSessionService raceCompetitionSeasonEventEntryService) {
        this.raceCompetitionSeasonEventSessionService = raceCompetitionSeasonEventEntryService;
    }

    @GetMapping("/sessions")
    public List<RaceCompetitionSeasonEventSessionResponseDto> findAllSessions() {
        return raceCompetitionSeasonEventSessionService.fetchAllSessions();
    }
}
