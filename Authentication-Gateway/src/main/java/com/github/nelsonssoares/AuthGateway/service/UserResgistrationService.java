package com.github.nelsonssoares.AuthGateway.service;

import com.github.nelsonssoares.AuthGateway.domain.dto.UserRequest;
import org.springframework.http.ResponseEntity;

public interface UserResgistrationService {

    ResponseEntity<UserRequest> registerUser(UserRequest userRequest) throws Exception;
}
