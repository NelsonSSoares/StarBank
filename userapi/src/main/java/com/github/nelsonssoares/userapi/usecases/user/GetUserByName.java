package com.github.nelsonssoares.userapi.usecases.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelsonssoares.userapi.commons.constraints.Constraints;
import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.domain.entities.User;
import com.github.nelsonssoares.userapi.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetUserByName {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    public Page<UserDTO> executeUserByName(String nome, Pageable pageable) {
        Page<User> usuarios = userRepository.findByNome(nome, pageable);

        Page<User> usuariosAtivos = Constraints.usersActivePage(usuarios);

        List<UserDTO> usuariosConverted = usuariosAtivos
                .stream()
                .map(user -> objectMapper.convertValue(user, UserDTO.class))
                .toList();

        return new PageImpl<>(usuariosConverted, pageable, usuariosAtivos.getTotalElements());
    }
}
