package com.github.nelsonssoares.AuthGateway.service.impl;

import com.github.nelsonssoares.AuthGateway.domain.dto.UserRequest;
import com.github.nelsonssoares.AuthGateway.outlayers.gateways.UserGateway;
import com.github.nelsonssoares.AuthGateway.service.UserResgistrationService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@RequiredArgsConstructor
@Service
public class UserResgistrationServiceImpl implements UserResgistrationService {

    private static final Logger log = LoggerFactory.getLogger(UserResgistrationServiceImpl.class);

    private final UserGateway userGateway;
    private final KafkaTemplate<String , Serializable> template;

    @CircuitBreaker(name = "registrationCB", fallbackMethod = "registerUserFallback")
    @Override
    public ResponseEntity<UserRequest> registerUser(UserRequest userRequest) throws Exception {

        UserRequest user = userGateway.createUser(userRequest);

        if (user == null || user.getId() == null || user.getId().describeConstable().isEmpty()) {
            throw new RuntimeException("User already exists or invalid data provided");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    public ResponseEntity<UserRequest> registerUserFallback(UserRequest userRequest, Throwable t) {



        if(template.send("registration-topic", userRequest).isDone()){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(userRequest);
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(userRequest);

        }

    }
}
