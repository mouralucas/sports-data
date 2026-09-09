package com.rolf.sports_data.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolf.sports_data.dto.participants.ParticipantRequestDto;
import com.rolf.sports_data.dto.participants.ParticipantResponseDto;
import com.rolf.sports_data.entities.ParticipantEntity;
import com.rolf.sports_data.entities.SportEntity;
import com.rolf.sports_data.mappers.ParticipantMapper;
import com.rolf.sports_data.repositories.ParticipantRepository;
import com.rolf.sports_data.repositories.SportRepository;

@Service
public class ParticipantService {

    private ParticipantRepository participantRepository;
    private SportRepository sportRepository;

    @Autowired
    public ParticipantService(ParticipantRepository participantRepository, SportRepository sportRepository) {
        this.participantRepository = participantRepository;
        this.sportRepository = sportRepository;
    }

    public String getParticipants() {
        return "Participants";
    }

    public ParticipantResponseDto createParticipant(ParticipantRequestDto participant) {
        ParticipantEntity newParticipant = ParticipantMapper.toEntity(participant);
        
        SportEntity sport = sportRepository.getReferenceById(participant.getSportId());
        newParticipant.setSport(sport);

        participantRepository.save(newParticipant);
        return ParticipantMapper.toResponse(newParticipant);
    }

}
