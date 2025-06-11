package com.github.nelsonssoares.AuthGateway.outlayers.gateways;

import com.github.nelsonssoares.AuthGateway.domain.dto.UserRequest;
import com.github.nelsonssoares.AuthGateway.outlayers.gateways.client.UserClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
@RequiredArgsConstructor
public class UserGateway {

    private final UserClient client;
//    private final KafkaTemplate<String, Serializable> template;

    //@CircuitBreaker(name = "registrationCB", fallbackMethod = "registerUserFallback")
    public UserRequest createUser( UserRequest userRequest)  {

        ResponseEntity<UserRequest> request = client.save(userRequest);

        if (request.getStatusCode().is2xxSuccessful()) {
            return request.getBody();
        } else {
            throw new RuntimeException("Failed to create user: " + request.getStatusCode());
        }
    }

//    public ResponseEntity<UserRequest> registerUserFallback(UserRequest userRequest, Throwable t) {
//
//        template.send("registration-topic", userRequest);
//        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userRequest);
//    }

}
