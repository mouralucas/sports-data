package com.rolf.sports_data.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rolf.sports_data.api.ApiRoutes;
import com.rolf.sports_data.dto.raceCar.RaceCarResponseDto;
import com.rolf.sports_data.services.RaceCarService;

@RestController 
@RequestMapping(ApiRoutes.API_V1)
public class RaceCarController {
    private RaceCarService raceCarService;

    public RaceCarController(RaceCarService raceCarService) {
        this.raceCarService = raceCarService;
    }

    @GetMapping ("/race-car")
    public List<RaceCarResponseDto> fetchRaceCars() {
        return raceCarService.fetchAllRaceCars();
    }
}
