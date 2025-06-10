package com.github.nelsonssoares.AuthGateway.service;

import com.github.nelsonssoares.AuthGateway.domain.dto.UserRequest;

public interface UserResgistrationService {

    UserRequest registerUser(UserRequest userRequest) throws Exception;
}
