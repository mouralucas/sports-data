package com.rolf.sports_data.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rolf.sports_data.api.ApiRoutes;
import com.rolf.sports_data.dto.venues.VenueResponseDto;
import com.rolf.sports_data.services.VenueService;

@RestController
@RequestMapping(ApiRoutes.API_V1)
public class VenueController {
    private VenueService venueService;

    @Autowired
    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping("/venues")
    public List<VenueResponseDto> getAllVenues() {
        return venueService.getVenues();
    }
}
