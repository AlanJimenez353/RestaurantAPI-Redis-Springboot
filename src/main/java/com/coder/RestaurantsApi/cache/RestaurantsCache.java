package com.coder.RestaurantsApi.cache;


public interface RestaurantsCache <T>{

    T save(String key,T data);
    T recover(String key,Class<T>classValue);
}
