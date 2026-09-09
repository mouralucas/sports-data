package com.rolf.sports_data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rolf.sports_data.entities.ParticipantEntity;

public interface ParticipantRepository extends JpaRepository<ParticipantEntity, Long> {
    List<ParticipantEntity> findByStatusTrue();
}
