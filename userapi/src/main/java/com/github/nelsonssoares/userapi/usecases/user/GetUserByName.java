package com.github.nelsonssoares.userapi.usecases.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelsonssoares.userapi.commons.constraints.Constraints;
import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.domain.entities.User;
import com.github.nelsonssoares.userapi.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetUserByName {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    public List<UserDTO> executeUserByName(String nome) {

        List<User> usuario = userRepository.findByNome(nome);

        List<User> usuariosAtivos = Constraints.usuariosAtivosList(usuario);
        List<UserDTO> usuariosConverted = new ArrayList<>();

        for (User usuarioAtivacted : usuariosAtivos) {
            UserDTO usuarioDto = objectMapper.convertValue(usuarioAtivacted, UserDTO.class);
            usuariosConverted.add(usuarioDto);
        }
        return usuariosConverted;

    }
}
