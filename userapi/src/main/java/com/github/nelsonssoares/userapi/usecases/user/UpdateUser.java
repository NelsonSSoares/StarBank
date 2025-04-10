package com.github.nelsonssoares.userapi.usecases.user;

import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.domain.entities.User;
import com.github.nelsonssoares.userapi.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateUser {

    private final UserRepository userRepository;

    @Transactional
    public User executeUpdateUser(Integer id, UserDTO userDTO) {

        Optional<User> usuario = userRepository.findById(id);

        if(usuario.isEmpty()){
            return null;
        }
        User user = usuario.get();
        user.setModificationDate(LocalDate.now());
        user.setName(userDTO.getName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());

        return userRepository.save(user);
    }

}
