package com.github.nelsonssoares.userapi.services;

import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.domain.entities.User;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    ResponseEntity<UserDTO> save(UserDTO dto);

    PagedModel<EntityModel<UserDTO>> findAll(Pageable paginacao);

    ResponseEntity<User> findById(Integer id);

    ResponseEntity<User> updateUser(Integer id, UserDTO userDTO);

    ResponseEntity<User> deleteUser(Integer id);

    PagedModel<EntityModel<UserDTO>> findAByName(String firstName, Pageable pageable);

    ResponseEntity<User> findByCpf(String cpf);

    ResponseEntity<User> reactiveUser(Integer id);

    ResponseEntity<UserDTO> findByEmail(String email);

    List<UserDTO> importFile(MultipartFile file) throws Exception;

    Resource exportFile(Pageable pageable, String acceptHeader);
}
