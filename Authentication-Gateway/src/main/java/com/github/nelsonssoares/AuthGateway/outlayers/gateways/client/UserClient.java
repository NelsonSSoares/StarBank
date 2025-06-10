package com.github.nelsonssoares.AuthGateway.outlayers.gateways.client;

import com.github.nelsonssoares.AuthGateway.domain.dto.UserRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
@FeignClient(name = "${feign.user-api.name}", url = "${feign.user-api.url}")
public interface UserClient {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<UserRequest> save(@RequestBody @Valid UserRequest dto);
}
