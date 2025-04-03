package com.github.nelsonssoares.userapi.usecases.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelsonssoares.userapi.commons.constants.enums.UserActive;
import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.domain.entities.User;
import com.github.nelsonssoares.userapi.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetUserByEmail {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public UserDTO executeUserByEmail(String email) {
        System.out.println(email);
        Optional<User> user = userRepository.findByEmail(email);
        System.out.println(user);
        if(user.isEmpty() || user.get().getActive().equals(UserActive.INACTIVE)){
            return null;
        }
        User useropt = user.get();
        return objectMapper.convertValue(useropt, UserDTO.class);
    }

}
