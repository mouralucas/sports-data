package com.rolf.sports_data.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rolf.sports_data.api.ApiRoutes;
import com.rolf.sports_data.dto.contructor.ConstructorResponseDto;
import com.rolf.sports_data.services.ConstructorService;

@RestController
@RequestMapping(ApiRoutes.API_V1)
public class ConstructorController {
    private final ConstructorService constructorService;

    public ConstructorController(ConstructorService constructorService) {
        this.constructorService = constructorService;
    }

    @GetMapping("/constructors")
    public List<ConstructorResponseDto> fetchConstructors() {
        return constructorService.fetchAllConstructors();
    }

    @GetMapping ("constructor/{id}")
    public ConstructorResponseDto getConstructorById(@PathVariable Long id) {
        return constructorService.fetchConstructorById(id);
    }
}
