package com.github.nelsonssoares.userapi.usecases.user;

import com.github.nelsonssoares.userapi.domain.entities.User;
import com.github.nelsonssoares.userapi.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetUserById {

    private final UserRepository userRepository;

    public User executeUserById(Integer id) {

        Optional<User> usuario = userRepository.findById(id);

        if(usuario.isEmpty()) {
            return null;
        }
        User user = usuario.get();
        return user;
    }
}
