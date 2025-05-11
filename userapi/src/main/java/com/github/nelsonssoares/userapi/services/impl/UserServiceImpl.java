package com.github.nelsonssoares.userapi.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelsonssoares.userapi.commons.constants.enums.UserActive;
import com.github.nelsonssoares.userapi.commons.constraints.Constraints;
import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.domain.entities.User;
import com.github.nelsonssoares.userapi.exceptions.FileStorageException;
import com.github.nelsonssoares.userapi.file.exporter.contract.FileExporter;
import com.github.nelsonssoares.userapi.file.exporter.factory.FileExporterFactory;
import com.github.nelsonssoares.userapi.file.importer.contract.FileImporter;
import com.github.nelsonssoares.userapi.file.importer.factory.FileImporterFactory;
import com.github.nelsonssoares.userapi.outlayers.entrypoints.UserController;
import com.github.nelsonssoares.userapi.services.UserService;
import com.github.nelsonssoares.userapi.usecases.user.*;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

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
    private final FileImporterFactory importer;
    private final FileExporterFactory exporter;
    private final PagedResourcesAssembler assembler;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public ResponseEntity<UserDTO> save(UserDTO dto) {

        UserDTO usuario = saveUser.executeSaveUser(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }

    @Override
    public PagedModel<EntityModel<UserDTO>> findAll(Pageable pageable) {
        Page<UserDTO> usuariosDtoPage = getAllUsers.executeAllUsers(pageable);

        if(usuariosDtoPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não há usuários cadastrados.");
        }

        usuariosDtoPage.forEach(this::addHateoasLinks);

        return assembler.toModel(usuariosDtoPage);
    }


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
    public PagedModel<EntityModel<UserDTO>> findAByName(String name, Pageable pageable) {
        Page<UserDTO> usuariosDtoPage = getUserByName.executeUserByName(name, pageable);

        if(usuariosDtoPage.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não há usuários cadastrados com este nome.");
        }

        usuariosDtoPage.forEach(this::addHateoasLinks);

        return assembler.toModel(usuariosDtoPage);
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

    @Override
    public List<UserDTO> importFile(MultipartFile file) throws Exception {

        logger.info("Importing file: {}", file.getOriginalFilename());

        if(file.isEmpty()) throw new BadRequestException("Ivalid file, please send a valid file");

        try(InputStream inputStream = file.getInputStream()){
            String fileName = Optional.ofNullable(file.getOriginalFilename())
                    .orElseThrow(()-> new BadRequestException("File name is null"));

            FileImporter importer = this.importer.getImporter(fileName);
            List<User> users = importer.importFile(inputStream).stream()
                    .map(userDTO -> objectMapper.convertValue(userDTO, User.class))
                    .toList();

            return users.stream().map(
                    user ->{
                        var dto = objectMapper.convertValue(user, UserDTO.class);
                        addHateoasLinks(dto);
                        return dto;
                    }
            ).toList();

        } catch (Exception e) {
            throw new FileStorageException("Error importing file", e);
        }
    }

    @Override
    public Resource exportFile(Pageable pageable, String acceptHeader) {
        logger.info("Exporting file with accept header: {}", acceptHeader);

        var users = getAllUsers.executeAllUsers(pageable)
                .map( user -> objectMapper.convertValue(user, UserDTO.class))
                .getContent();

        try{
            FileExporter exporter = this.exporter.getExporter(acceptHeader);
            return exporter.exportFile(users);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void addHateoasLinks(UserDTO dto) {
        dto.add(linkTo(methodOn(UserController.class).findById(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(UserController.class).deleteUser(dto.getId())).withRel("deleteUser").withType("DELETE"));
        dto.add(linkTo(methodOn(UserController.class).findAll(0, 10, "asc")).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(UserController.class).save(dto)).withRel("save").withType("POST"));
        dto.add(linkTo(methodOn(UserController.class).updateUser(dto.getId(), dto)).withRel("updateUser").withType("PUT"));
        dto.add(linkTo(methodOn(UserController.class).activeUser(dto.getId())).withRel("activeUser").withType("PUT"));
        dto.add(linkTo(methodOn(UserController.class).findByName(dto.getName(), 0, 10, "asc")).withRel("findByName").withType("GET"));
        dto.add(linkTo(methodOn(UserController.class).findByCPF(dto.getCpf())).withRel("findByCpf").withType("GET"));
        dto.add(linkTo(methodOn(UserController.class).findByEmail(dto.getEmail())).withRel("findByEmail").withType("GET"));
        dto.add(linkTo(methodOn(UserController.class)).slash("massCreation").withRel("massCreation").withType("POST"));

    }
}
