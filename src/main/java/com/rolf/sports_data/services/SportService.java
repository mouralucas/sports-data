package com.rolf.sports_data.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rolf.sports_data.dto.sports.GetSpostsRequestDto;
import com.rolf.sports_data.dto.sports.SportRequestDto;
import com.rolf.sports_data.dto.sports.SportResponsetDto;
import com.rolf.sports_data.entities.SportEntity;
import com.rolf.sports_data.mappers.SportMapper;
import com.rolf.sports_data.repositories.SportRepository;

@Service
public class SportService {
    private final SportRepository sportRepository;

    public SportService(SportRepository sportRepository) {
        this.sportRepository = sportRepository;
    }


    public List<String> getAllSports(GetSpostsRequestDto params) {
        var sports = sportRepository.findAll();

        return null;
        // return sports.stream()
        //         .map(SportMapper::toResponse)
        //         .toList();
    }

    public SportResponsetDto createStport(SportRequestDto sport) {
        SportEntity newSport = SportMapper.toEntity(sport);

        // TODO: create a service to build slugs
        String slug = sport.getName().replace(" ", "-");
        newSport.setSlug(slug);

        return SportMapper.toResponse(sportRepository.save(newSport));
    }
}
