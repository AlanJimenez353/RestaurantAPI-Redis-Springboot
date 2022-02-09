package com.coder.RestaurantsApi.cache;

import com.coder.RestaurantsApi.config.ApplicationProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.coder.RestaurantsApi.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


import javax.annotation.PostConstruct;
import java.time.Duration;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestaurantCacheImp<T> implements RestaurantsCache<T> {

    private final RedisTemplate<String,T> redisTemplate;
    private final ApplicationProperties properties;
    private HashOperations<String,String,String>hashOperation;
    private final ObjectMapper mapper;

    @PostConstruct
    void setHashOperations() {
        hashOperation = this.redisTemplate.opsForHash();
        this.redisTemplate.expire(Constants.NAME_MAP_RESTAURANT, Duration.ofMillis(properties.getTimeOfLife()));
    }

    @Override
    public T save(String key, T data) {
        try {
            hashOperation.put("restaurantMap", key, serializeItem(data));
            return data;
        } catch (JsonProcessingException e) {
            log.error("Error converting message to string", e);
        }
        return data;
    }

    @Override
    public T recover(String key, Class<T> classValue) {
        try {
            var item = hashOperation.get("RestaurantMap", key);
            if (item == null) return null;
            return deserializeItem(item, classValue);
        } catch (JsonProcessingException e) {
            log.error("Error converting message to Message", e);
        }
        return null;
    }

    public String serializeItem(T item) throws JsonProcessingException {
        var serializeItem = mapper.writeValueAsString(item);
        log.info("Restaurant en formato String:{}", serializeItem);
        return serializeItem;
    }

    public T deserializeItem(String jsonInput, Class<T> classValue) throws JsonProcessingException {
        return mapper.readValue(jsonInput, classValue);
    }



}
