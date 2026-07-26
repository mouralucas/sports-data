package com.rolf.sports_data.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rolf.sports_data.entities.SportEntity;

public interface SportRepository extends JpaRepository<SportEntity, Long> {
    List<SportEntity> findByActiveTrue();
}
