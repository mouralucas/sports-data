package com.rolf.sports_data.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rolf.sports_data.dto.raceCar.RaceCarResponseDto;
import com.rolf.sports_data.entities.RaceCarEntity;
import com.rolf.sports_data.mappers.RaceCarMapper;
import com.rolf.sports_data.repositories.RaceCarRepository;

@Service 
public class RaceCarService {
    private final RaceCarRepository raceCarRepository;

    public RaceCarService(RaceCarRepository raceCarRepository) {
        this.raceCarRepository = raceCarRepository;
    }

    public List<RaceCarResponseDto> fetchAllRaceCars() {
        List<RaceCarEntity> raceCar = raceCarRepository.findAll();

        return RaceCarMapper.toListResponse(raceCar);
    }

    public String createRaceCar() {
        return "Em construção";
    }
}
