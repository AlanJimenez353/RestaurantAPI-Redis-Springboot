package com.coder.RestaurantsApi.repository;



import com.coder.RestaurantsApi.model.Restaurant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RestaurantRepository extends CrudRepository<Restaurant, Long> {

}
