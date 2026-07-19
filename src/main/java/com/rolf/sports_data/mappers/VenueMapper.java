package com.rolf.sports_data.mappers;

import java.util.List;

import com.rolf.sports_data.dto.venues.VenueResponseDto;
import com.rolf.sports_data.entities.VenueEntity;

public class VenueMapper {
    public static VenueResponseDto toResponse(VenueEntity venue) {
        String venueType = venue.getVenueType().getDescription();

        return new VenueResponseDto(venue.getId(),
                venue.getName(),
                venueType,
                venue.getCapacity(),
                venue.getLengthMeters(),
                venue.isActive());
    }

    public static List<VenueResponseDto> toListResponse(List<VenueEntity> venues) {
        return venues.stream()
                .map(VenueMapper::toResponse)
                .toList();
    }
}
