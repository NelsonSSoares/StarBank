package com.github.nelsonssoares.userapi.usecases.user;

import com.github.nelsonssoares.userapi.commons.constants.enums.UserActive;
import com.github.nelsonssoares.userapi.domain.entities.User;
import com.github.nelsonssoares.userapi.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetUserByCpf {

private final UserRepository userRepository;

    public User executeUserByCpf(String cpf) {

        Optional<User> user = userRepository.findByCpf(cpf);

        if(user.isEmpty() || user.get().getActive().equals(UserActive.INACTIVE)){
            return null;
        }
        return user.get();
    }

}
