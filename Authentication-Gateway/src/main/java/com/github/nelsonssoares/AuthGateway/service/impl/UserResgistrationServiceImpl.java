package com.github.nelsonssoares.AuthGateway.service.impl;

import com.github.nelsonssoares.AuthGateway.domain.dto.UserRequest;
import com.github.nelsonssoares.AuthGateway.outlayers.gateways.UserGateway;
import com.github.nelsonssoares.AuthGateway.service.UserResgistrationService;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserResgistrationServiceImpl implements UserResgistrationService {

    private final UserGateway userGateway;

    @Override
    public UserRequest registerUser(UserRequest userRequest) throws Exception {

        UserRequest user = userGateway.createUser(userRequest);

        if (user == null) {
            throw new BadRequestException("User already exists or invalid data provided");
        }
        return user;
    }
}
