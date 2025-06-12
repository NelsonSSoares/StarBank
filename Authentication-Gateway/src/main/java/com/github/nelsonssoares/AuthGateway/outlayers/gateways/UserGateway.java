package com.github.nelsonssoares.AuthGateway.outlayers.gateways;

import com.github.nelsonssoares.AuthGateway.domain.dto.UserRequest;
import com.github.nelsonssoares.AuthGateway.outlayers.gateways.client.UserClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserGateway {

    private final UserClient client;

    public UserRequest createUser( UserRequest userRequest)  {

        ResponseEntity<UserRequest> request = client.save(userRequest);

        if (request.getStatusCode().is2xxSuccessful()) {
            return request.getBody();
        } else {
            throw new RuntimeException("Failed to create user: " + request.getStatusCode());
        }
    }


}
