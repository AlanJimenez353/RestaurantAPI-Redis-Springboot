package com.coder.RestaurantsApi.service;


import com.coder.RestaurantsApi.model.Restaurant;
import com.coder.RestaurantsApi.repository.RestaurantRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.coder.RestaurantsApi.cache.RestaurantsCache;
import com.coder.RestaurantsApi.handle.ApiRestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImp implements RestaurantService{

    private final RestaurantRepository repository;
    private final ObjectMapper mapper;
    private final RestaurantsCache<Restaurant> cache;

    @Override
    public Restaurant create(Restaurant restaurant) {
        try {
            classToString(restaurant);
            mapToClass(restaurant);
            return saveRestaurantInCache(restaurant);
        } catch (JsonProcessingException e) {
            log.error("Error converting Restaurante to string /map /class", e);
        }
        return restaurant;
    }
    public Map<String, Restaurant> restaurantToMap(String restaurant) throws  JsonProcessingException{
        Map<String, Restaurant> resMap = mapper.readValue(restaurant, Map.class);
        return resMap;
    }
    @Override
    public Restaurant findById(Long id) {
        try {
            if (id == 0) {
                throw ApiRestException.builder().message("El identificador del restaurante debe ser mayor a 0").build();
            }

            var dataFromCache = cache.recover(id.toString(), Restaurant.class);
            if (!Objects.isNull(dataFromCache)) {
                return dataFromCache;
            }
            var dataFromDatabase = repository.findById(id).orElseThrow(ApiRestException::new);
            return saveRestaurantInCache(dataFromDatabase);
        } catch (JsonProcessingException e) {
            log.error("Error converting message to string", e);
        } catch (ApiRestException e) {
            e.printStackTrace();
        }
        return null;
    }

    void classToString(Restaurant restaurant) throws JsonProcessingException {
        var restaurantString = mapper.writeValueAsString(restaurant);
        log.info("Restaurante en formato String : {}", restaurantString);
    }


    void mapToClass(Restaurant restaurant) throws JsonProcessingException {
        var stringFormat = mapper.writeValueAsString(restaurant);
        var classFormat = mapper.readValue(stringFormat, Restaurant.class);
        log.info("Restaurante en formato de Clase : {}", classFormat);
    }

    public Map<String, Restaurant> stringToMap(String restaurant) throws JsonProcessingException {
        Map<String, Restaurant> resMap = mapper.readValue(restaurant, Map.class);
        return resMap;
    }






    private Restaurant saveRestaurantInCache(Restaurant restaurant)throws JsonProcessingException{
        return cache.save(restaurant.getId().toString(),restaurant);
    }



}
