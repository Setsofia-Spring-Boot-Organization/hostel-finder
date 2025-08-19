package com.backend.hostel_finder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class HostelFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(HostelFinderApplication.class, args);
	}

}
