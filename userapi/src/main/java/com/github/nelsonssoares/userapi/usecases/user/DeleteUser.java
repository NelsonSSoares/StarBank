package com.github.nelsonssoares.userapi.usecases.user;


import com.github.nelsonssoares.userapi.commons.constants.enums.UserActive;
import com.github.nelsonssoares.userapi.domain.entities.User;
import com.github.nelsonssoares.userapi.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeleteUser {

    private final UserRepository userRepository;

    @Transactional
    public User executeDeleteUser(Integer id) {

        Optional<User> usuario = userRepository.findById(id);

        if (usuario.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        } else if (usuario.get().getActive().equals(UserActive.INACTIVE)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuário já inativo");
        }


        User user  = usuario.get();
        user.setActive(UserActive.INACTIVE);


        return userRepository.save(user);
    }
}
