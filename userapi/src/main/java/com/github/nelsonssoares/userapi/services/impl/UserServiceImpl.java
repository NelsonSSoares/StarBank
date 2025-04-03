package com.github.nelsonssoares.userapi.services.impl;

import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.domain.entities.User;
import com.github.nelsonssoares.userapi.services.UserService;
import com.github.nelsonssoares.userapi.usecases.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final SaveUser saveUser;
    private final GetAllUsers getAllUsers;
    private final GetUserById getUserById;
    private final UpdateUser updateUser;
    private final DeleteUser deleteUser;
    private final GetUserByName getUserByName;
    private final GetUserByCpf getUserByCpf;
    private final GetUserByEmail getUserByEmail;
    private final ActiveUser activeUser;

    @Override
    public ResponseEntity<UserDTO> save(UserDTO dto) {

        UserDTO usuario = saveUser.executeSaveUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @Override
    public ResponseEntity<List<UserDTO>> findAll(Pageable paginacao) {

        List<UserDTO> usuarios = getAllUsers.executeAllUsers(paginacao);

        if(usuarios.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @Override
    public ResponseEntity<User> findById(Integer id) {

        User usuario = getUserById.executeUserById(id);

        if(usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else if(usuario.getAtivo().equals(PerguntaAtivo.NAO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @Override
    public ResponseEntity<User> updateUser(Integer id, UserDTO userDTO) {

        User usuario = updateUser.executeUpdateUser(id, userDTO);

        if(usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (usuario.getAtivo().equals(PerguntaAtivo.NAO)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @Override
    public ResponseEntity<User> deleteUser(Integer id) {
        User usuario = deleteUser.executeDeleteUser(id);

        if(usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (usuario.getAtivo().equals(PerguntaAtivo.NAO)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<List<UserDTO>> findByName(String nome) {

        List<UserDTO> usuarios = getUserByName.executeUserByName(nome);

        return usuarios.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(usuarios);
    }

    @Override
    public ResponseEntity<User> findByCpf(String cpf) {

        User usuario = getUserByCpf.executeUserByCpf(cpf);

        return usuario == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(usuario);
    }

    @Override
    public ResponseEntity<User> reactiveUser(Integer id) {

        User usuario = activeUser.executeActiveUser(id);

        return usuario == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(usuario);
    }

    @Override
    public ResponseEntity<UserDTO> findByEmail(String email) {

        UserDTO usuario = getUserByEmail.executeUserByEmail(email);

        return usuario == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(usuario);
    }
}
