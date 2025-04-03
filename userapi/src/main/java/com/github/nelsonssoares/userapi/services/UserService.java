package com.github.nelsonssoares.userapi.services;

import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.domain.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    ResponseEntity<UserDTO> save(UserDTO dto);

    ResponseEntity<List<UserDTO>> findAll(Pageable paginacao);

    ResponseEntity<User> findById(Integer id);

    ResponseEntity<User> updateUser(Integer id, UserDTO userDTO);

    ResponseEntity<User> deleteUser(Integer id);

    ResponseEntity<List<UserDTO>> findByName(String nome);

    ResponseEntity<User> findByCpf(String cpf);

    ResponseEntity<User> reactiveUser(Integer id);

    ResponseEntity<UserDTO> findByEmail(String email);
}
