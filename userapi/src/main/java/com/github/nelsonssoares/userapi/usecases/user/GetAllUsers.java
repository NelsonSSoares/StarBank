package com.github.nelsonssoares.userapi.usecases.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelsonssoares.userapi.commons.constraints.Constraints;
import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.domain.entities.User;
import com.github.nelsonssoares.userapi.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllUsers {

    private final UserRepository usuarioRepository;
    private final ObjectMapper objectMapper;

    public List<UserDTO> executeAllUsers(Pageable paginacao) {

        Page<User> usuarios =  usuarioRepository.findAll(paginacao);
        Page<User> usuariosAtivos = Constraints.usersActivePage(usuarios);
        List<UserDTO> usuariosConverted = new ArrayList<>();
        for (User usuarioAtivacted : usuariosAtivos) {
            UserDTO usuarioDto = objectMapper.convertValue(usuarioAtivacted, UserDTO.class);
            usuariosConverted.add(usuarioDto);
        }

        return usuariosConverted.isEmpty() ? null : usuariosConverted;
    }
}
