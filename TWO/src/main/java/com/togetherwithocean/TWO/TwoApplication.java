package com.togetherwithocean.TWO;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TwoApplication {

	public static void main(String[] args) {

		SpringApplication.run(TwoApplication.class, args);
	}
}
