package com.github.nelsonssoares.userapi.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelsonssoares.userapi.commons.constants.enums.UserActive;
import com.github.nelsonssoares.userapi.commons.constraints.Constraints;
import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.domain.entities.User;
import com.github.nelsonssoares.userapi.outlayers.entrypoints.UserController;
import com.github.nelsonssoares.userapi.services.UserService;
import com.github.nelsonssoares.userapi.usecases.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
    private final ObjectMapper objectMapper;
    private final PagedResourcesAssembler assembler;

    @Override
    public ResponseEntity<UserDTO> save(UserDTO dto) {

        UserDTO usuario = saveUser.executeSaveUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @Override
    public PagedModel<EntityModel<UserDTO>> findAll(Pageable pageable) {
        Page<UserDTO> usuariosDtoPage = getAllUsers.executeAllUsers(pageable);

        usuariosDtoPage.forEach(this::addHateoasLinks);

        return assembler.toModel(usuariosDtoPage);
    }

//    @Override
//    public ResponseEntity<List<UserDTO>> findAll(Pageable paginacao) {
//
//        List<UserDTO> usuarios = getAllUsers.executeAllUsers(paginacao);
//
//        if(usuarios.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//        }
//        for (UserDTO usuario : usuarios) {
//            addHateoasLinks(usuario);
//        }
//        return ResponseEntity.ok(usuarios);
//    }

    @Override
    public ResponseEntity<User> findById(Integer id) {

        User usuario = getUserById.executeUserById(id);

        if(usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }else if(usuario.getActive().equals(UserActive.INACTIVE)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        UserDTO dto = objectMapper.convertValue(usuario, UserDTO.class);
        addHateoasLinks(dto);

        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @Override
    public ResponseEntity<User> updateUser(Integer id, UserDTO userDTO) {

        User usuario = updateUser.executeUpdateUser(id, userDTO);

        if(usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (usuario.getActive().equals(UserActive.INACTIVE)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        UserDTO dto = objectMapper.convertValue(usuario, UserDTO.class);
        addHateoasLinks(dto);

        return ResponseEntity.status(HttpStatus.OK).body(usuario);
    }

    @Override
    public ResponseEntity<User> deleteUser(Integer id) {
        User usuario = deleteUser.executeDeleteUser(id);

        if(usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else if (usuario.getActive().equals(UserActive.INACTIVE)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        UserDTO dto = objectMapper.convertValue(usuario, UserDTO.class);
        addHateoasLinks(dto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<List<UserDTO>> findByName(String nome) {

        List<UserDTO> usuarios = getUserByName.executeUserByName(nome);

        for (UserDTO usuario : usuarios) {
            addHateoasLinks(usuario);
        }

        return usuarios.isEmpty() ? ResponseEntity.status(HttpStatus.NO_CONTENT).build() : ResponseEntity.ok(usuarios);
    }

    @Override
    public ResponseEntity<User> findByCpf(String cpf) {

        User usuario = getUserByCpf.executeUserByCpf(cpf);

        UserDTO dto = objectMapper.convertValue(usuario, UserDTO.class);
        addHateoasLinks(dto);

        return usuario == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(usuario);
    }

    @Override
    public ResponseEntity<User> reactiveUser(Integer id) {

        User usuario = activeUser.executeActiveUser(id);

        UserDTO dto = objectMapper.convertValue(usuario, UserDTO.class);
        addHateoasLinks(dto);

        return usuario == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(usuario);
    }

    @Override
    public ResponseEntity<UserDTO> findByEmail(String email) {

        UserDTO usuario = getUserByEmail.executeUserByEmail(email);

        addHateoasLinks(usuario);

        return usuario == null ? ResponseEntity.status(HttpStatus.NOT_FOUND).build() : ResponseEntity.ok(usuario);
    }

    private void addHateoasLinks(UserDTO dto) {
        dto.add(linkTo(methodOn(UserController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(UserController.class).deleteUser(dto.getId())).withRel("deleteUser").withType("DELETE"));
        dto.add(linkTo(methodOn(UserController.class).findAll(0, 10, "asc")).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(UserController.class).save(dto)).withRel("save").withType("POST"));
        dto.add(linkTo(methodOn(UserController.class).updateUser(dto.getId(), dto)).withRel("updateUser").withType("PUT"));
        dto.add(linkTo(methodOn(UserController.class).activeUser(dto.getId())).withRel("activeUser").withType("PUT"));
        dto.add(linkTo(methodOn(UserController.class).findByName(dto.getName())).withRel("findByName").withType("GET"));
        dto.add(linkTo(methodOn(UserController.class).findByCPF(dto.getCpf())).withRel("findByCpf").withType("GET"));
        dto.add(linkTo(methodOn(UserController.class).findByEmail(dto.getEmail())).withRel("findByEmail").withType("GET"));

    }
}
