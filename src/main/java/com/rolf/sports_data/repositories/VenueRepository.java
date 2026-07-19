package com.rolf.sports_data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rolf.sports_data.entities.VenueEntity;

public interface VenueRepository extends JpaRepository<VenueEntity, Long>{
    List<VenueEntity> findAll();
}
