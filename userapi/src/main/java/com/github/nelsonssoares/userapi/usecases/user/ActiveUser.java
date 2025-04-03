package com.github.nelsonssoares.userapi.usecases.user;


import com.github.nelsonssoares.userapi.commons.constants.enums.UserActive;
import com.github.nelsonssoares.userapi.domain.entities.User;
import com.github.nelsonssoares.userapi.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ActiveUser {

    private final UserRepository userRepository;

    @Transactional
    public User executeActiveUser(Integer id) {
        Optional<User> usuario = userRepository.findById(id);
        if(usuario.isEmpty()){
            return null;
        } else if (usuario.get().getActive().equals(UserActive.ACTIVE)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Usuário já está ativo!");
        }
        User user = usuario.get();
        user.setModificationDate(LocalDate.now());
        user.setActive(UserActive.ACTIVE);
        return userRepository.save(user);
    }
}
