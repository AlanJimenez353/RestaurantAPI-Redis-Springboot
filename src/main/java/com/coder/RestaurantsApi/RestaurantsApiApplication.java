package com.coder.RestaurantsApi;

import com.coder.RestaurantsApi.model.Restaurant;
import com.coder.RestaurantsApi.repository.RestaurantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RestaurantsApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantsApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(RestaurantRepository repository) {
		return (args) -> {
			repository.save(Restaurant.builder().id(1L).fecha_creacion("2000-2-20").name("Adorado").hora_fin("24").hora_inicio("16").build());
			repository.save(Restaurant.builder().id(2L).fecha_creacion("2200-9-10").name("La fonte").hora_fin("20").hora_inicio("14").build());
			repository.save(Restaurant.builder().id(3L).fecha_creacion("2020-8-23").name("La Boston").hora_fin("22").hora_inicio("17").build());
			repository.save(Restaurant.builder().id(4L).fecha_creacion("2020-7-12").name("Crossaint").hora_fin("10").hora_inicio("8").build());
			repository.save(Restaurant.builder().id(5L).fecha_creacion("2020-6-13").name("CoffeMax").hora_fin("12").hora_inicio("16").build());

		};
	}
}
