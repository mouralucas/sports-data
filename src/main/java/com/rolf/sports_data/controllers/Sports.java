package com.rolf.sports_data.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Sports extends ApiVersionV1Controller {

    @GetMapping("/sports")
    public String listSports() {
        return "sports";
    }
}
