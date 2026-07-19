package com.rolf.sports_data.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rolf.sports_data.api.ApiRoutes;
import com.rolf.sports_data.dto.sports.GetSpostsRequestDto;
import com.rolf.sports_data.dto.sports.SportRequestDto;
import com.rolf.sports_data.dto.sports.SportResponseDto;
import com.rolf.sports_data.services.SportService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiRoutes.API_V1)
public class SportController {

    private final SportService sportService;

    @Autowired
    public SportController(SportService sportService) {
        this.sportService = sportService;
    }

    @GetMapping("/sport/{id}")
    public SportResponseDto getSportById(@PathVariable Long id) {
        return sportService.getSportById(id);
    }

    @GetMapping("/sports")
    public List<SportResponseDto> listSports(@Valid @ModelAttribute GetSpostsRequestDto params) {
        return sportService.getAllSports(params);
    }

    @PostMapping("/sport")
    public SportResponseDto createSport(@RequestBody SportRequestDto sport) {
        return sportService.createStport(sport);
    }
}
