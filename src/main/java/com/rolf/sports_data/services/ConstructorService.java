package com.rolf.sports_data.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.rolf.sports_data.dto.contructor.ConstructorResponseDto;
import com.rolf.sports_data.entities.ConstructorEntity;
import com.rolf.sports_data.mappers.ConstructorMapper;
import com.rolf.sports_data.repositories.ConstructorRepository;

@Service 
public class ConstructorService {
    private final ConstructorRepository constructorRepository;

    public ConstructorService(ConstructorRepository constructorRepository) {
        this.constructorRepository = constructorRepository;
    }

    public List<ConstructorResponseDto> fetchAllConstructors() {
        List<ConstructorEntity> constructors = constructorRepository.findAll();

        return ConstructorMapper.toListResponse(constructors);
    }

    public ConstructorResponseDto fetchConstructorById(Long id) {
        ConstructorEntity constructor = constructorRepository.getReferenceById(id);
        return ConstructorMapper.toResponse(constructor);
    }
}
