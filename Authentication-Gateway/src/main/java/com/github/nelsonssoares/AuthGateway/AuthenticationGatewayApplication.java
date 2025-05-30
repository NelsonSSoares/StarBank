package com.github.nelsonssoares.AuthGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class AuthenticationGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationGatewayApplication.class, args);
	}

}
