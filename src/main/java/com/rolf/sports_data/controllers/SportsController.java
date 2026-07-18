package com.rolf.sports_data.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rolf.sports_data.api.ApiRoutes;
import com.rolf.sports_data.dto.sports.GetSpostsRequestDto;
import com.rolf.sports_data.dto.sports.SportRequestDto;
import com.rolf.sports_data.dto.sports.SportResponsetDto;
import com.rolf.sports_data.services.SportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiRoutes.API_V1)
public class SportsController {

    private final SportService sportService;

    @Autowired
    public SportsController(SportService sportService) {
        this.sportService = sportService;
    }

    @GetMapping("/sports")
    public String listSports(@Valid @ModelAttribute GetSpostsRequestDto params) {
        return "Ainda em testes";
    }

    @PostMapping("/sport")
    public SportResponsetDto createSport(@RequestBody SportRequestDto sport) {
        return sportService.createStport(sport);
    }
}
