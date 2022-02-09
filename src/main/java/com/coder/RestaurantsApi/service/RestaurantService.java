package com.coder.RestaurantsApi.service;

import com.coder.RestaurantsApi.model.Restaurant;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.Map;


public interface RestaurantService {

    Restaurant create(Restaurant restaurant);
    Restaurant findById(Long id);
    Map<String, Restaurant> stringToMap(String restaurant) throws JsonProcessingException;
}