package com.cwi.cooperativa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CooperativaApplication {

	public static void main(String[] args) {
		SpringApplication.run(CooperativaApplication.class, args);
	}

}
