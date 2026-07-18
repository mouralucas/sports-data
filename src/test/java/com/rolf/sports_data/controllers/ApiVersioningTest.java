package com.rolf.sports_data.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ApiVersioningTest {

    @Test
    void shouldConfigureV1BaseControllerMapping() {
        RequestMapping requestMapping = ApiVersionV1Controller.class.getAnnotation(RequestMapping.class);
        assertNotNull(requestMapping);
        assertEquals("/api/v1", requestMapping.value()[0]);

        Method helloMethod = HelloV1.class.getDeclaredMethods()[0];
        assertNotNull(helloMethod);

        Method sportsMethod = Sports.class.getDeclaredMethods()[0];
        assertNotNull(sportsMethod);
    }
}
