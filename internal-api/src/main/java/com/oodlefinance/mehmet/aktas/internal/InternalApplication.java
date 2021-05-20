package com.oodlefinance.mehmet.aktas.internal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class InternalApplication {

	public static void main(String[] args) {
		SpringApplication.run(InternalApplication.class, args);
	}

}
