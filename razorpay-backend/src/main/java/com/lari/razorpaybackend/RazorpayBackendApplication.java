package com.lari.razorpaybackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RazorpayBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(RazorpayBackendApplication.class, args);
	}

}
