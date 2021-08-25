package com.nayax.intern.microservices.document_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableFeignClients
@EnableScheduling
public class DocumentManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DocumentManagerApplication.class, args);
	}

}
