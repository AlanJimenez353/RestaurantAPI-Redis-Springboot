package com.coder.RestaurantsApi.controller;

import com.coder.RestaurantsApi.model.Restaurant;
import com.coder.RestaurantsApi.service.RestaurantService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("coder-house")
public class RestaurantController {

    private final RestaurantService service;

    @GetMapping("/rest/{id}")
    public Restaurant getRestaurantById(@PathVariable Long id) {
        log.info("GET obtener Restaurante por el id");
        return service.findById(id);
    }

    @PostMapping("/rest")
    public Restaurant createRestaurant(@RequestBody Restaurant rest) {
        log.info("POST crear Restaurante");
        return service.create(rest);
    }
    @PostMapping("/rest/map")
    public Map<String, Restaurant> stringRestToMap(@RequestBody @Validated String restaurant) throws JsonProcessingException {
        return service.stringToMap(restaurant);
    }



}
