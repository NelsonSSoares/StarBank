package com.github.nelsonssoares.AuthGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@EnableDiscoveryClient
@SpringBootApplication
public class AuthenticationGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationGatewayApplication.class, args);
	}

}
