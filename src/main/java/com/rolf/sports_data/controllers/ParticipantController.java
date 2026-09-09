package com.rolf.sports_data.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rolf.sports_data.api.ApiRoutes;
import com.rolf.sports_data.dto.participants.ParticipantRequestDto;
import com.rolf.sports_data.dto.participants.ParticipantResponseDto;
import com.rolf.sports_data.services.ParticipantService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ApiRoutes.API_V1)
public class ParticipantController {

    private ParticipantService participantService;

    @Autowired 
    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @GetMapping("/participant/{id}")
    public String getParticipantById(@PathVariable Long id) {
        return "Em construção";
    }

    @GetMapping("/participants")
    public String getParticipants() {
        return "Em construção";
    }

    @PostMapping("/participant")
    public ParticipantResponseDto createParticipant(
        @Valid @RequestBody ParticipantRequestDto participant
    ) {
        return participantService.createParticipant(participant);
    }
}