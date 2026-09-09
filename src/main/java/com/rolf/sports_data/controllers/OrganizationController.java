package com.rolf.sports_data.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rolf.sports_data.api.ApiRoutes;

@RestController
@RequestMapping(ApiRoutes.API_V1)
public class OrganizationController {

    @GetMapping("/organization/{id}")
    public String getOrganizationById(@PathVariable Long id) {
        return "Em construção";
    } 

    public void getOrganizations() {

    }
}
