package com.rolf.sports_data.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rolf.sports_data.dto.venues.VenueResponseDto;
import com.rolf.sports_data.entities.VenueEntity;
import com.rolf.sports_data.mappers.VenueMapper;
import com.rolf.sports_data.repositories.VenueRepository;

@Service
public class VenueService {
    private VenueRepository venueRepository;

    @Autowired
    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public List<VenueResponseDto> getVenues() {
        List<VenueEntity> venues = venueRepository.findAll();
        return VenueMapper.toListResponse(venues);
    }
}
