package com.rolf.sports_data.controllers;


import com.rolf.sports_data.api.ApiRoutes;
import com.rolf.sports_data.dto.raceCompetitonSeasonEventEntry.RaceCompetitionSeasonEventEntryResponseDto;
import com.rolf.sports_data.services.RaceCompetitionSeasonEventEntryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(ApiRoutes.API_V1)
public class RaceCompetitionSeasonEventEntryController {
    private final RaceCompetitionSeasonEventEntryService raceCompetitionSeasonEventEntryService;

    public RaceCompetitionSeasonEventEntryController(RaceCompetitionSeasonEventEntryService raceCompetitionSeasonEventEntryService) {
        this.raceCompetitionSeasonEventEntryService = raceCompetitionSeasonEventEntryService;
    }

    @GetMapping("/event/entries")
    public List<RaceCompetitionSeasonEventEntryResponseDto> getAllEntriesPerEvent() {
        return raceCompetitionSeasonEventEntryService.fetchAllCompetitionsEntryByEvent(1L);
    }
}
