package com.zdanovich.module_publisher;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;

import static org.springframework.web.client.RestClient.builder;

@SpringBootApplication
public class DistributedComputingApplication {

	public static void main(String[] args) {
		SpringApplication.run(DistributedComputingApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public RestClient restClient() {
		return builder().baseUrl("http://localhost:24130/api/v1.0/messages").build();
	}
}
