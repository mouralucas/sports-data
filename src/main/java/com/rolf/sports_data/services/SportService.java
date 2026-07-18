package com.rolf.sports_data.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rolf.sports_data.dto.sports.GetSpostsRequestDto;
import com.rolf.sports_data.dto.sports.SportRequestDto;
import com.rolf.sports_data.dto.sports.SportResponseDto;
import com.rolf.sports_data.entities.SportEntity;
import com.rolf.sports_data.mappers.SportMapper;
import com.rolf.sports_data.repositories.SportRepository;

@Service
public class SportService {
    private final SportRepository sportRepository;

    public SportService(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }

    public SportResponseDto getSportById(Long id) {
        SportEntity sport = sportRepository.getReferenceById(id);
        return SportMapper.toResponse(sport);
    }

    public List<SportResponseDto> getAllSports(GetSpostsRequestDto params) {
        var sports = sportRepository.findAll();

        return SportMapper.toListResponse(sports);
    }

    public SportResponseDto createStport(SportRequestDto sport) {
        SportEntity newSport = SportMapper.toEntity(sport);
        newSport.setSlug(SlugService.buildSlug(sport.getName()));

        return SportMapper.toResponse(sportRepository.save(newSport));
    }
}
