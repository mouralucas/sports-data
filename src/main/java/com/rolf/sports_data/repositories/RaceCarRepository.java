package com.rolf.sports_data.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rolf.sports_data.entities.RaceCarEntity;

public interface RaceCarRepository extends JpaRepository<RaceCarEntity, Long> {
    
}
