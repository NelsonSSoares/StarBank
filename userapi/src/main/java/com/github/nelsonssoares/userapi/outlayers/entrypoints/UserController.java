package com.github.nelsonssoares.userapi.outlayers.entrypoints;


import com.github.nelsonssoares.userapi.commons.constants.controllers.ControllersConstants;
import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.domain.entities.User;
import com.github.nelsonssoares.userapi.outlayers.entrypoints.docs.UserControllerDoc;
import com.github.nelsonssoares.userapi.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.nelsonssoares.userapi.commons.constants.controllers.ControllersConstants.*;
import static org.springframework.http.MediaType.*;

@Tag(name = API_TAG, description = API_DESCRIPTION)
@RequiredArgsConstructor
@RestController
@RequestMapping(value = API_BASE_URL, produces = {APPLICATION_JSON_VALUE, APPLICATION_XML_VALUE, APPLICATION_YAML_VALUE})
//@SecurityRequirement(name = API_SECURITY_REQUIREMENT)
public class UserController implements UserControllerDoc {

    private final UserService userService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<UserDTO> save(@RequestBody @Valid UserDTO dto) {
        //Retornar entidade Usuario com ID
        return userService.save(dto);
    }


    @PutMapping(value = ID)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody @Valid UserDTO userDTO) {
        return userService.updateUser(id, userDTO);
    }

    @PutMapping(value = ACTIVE)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<User> activeUser(@PathVariable("id") Integer id) {
        return userService.reactiveUser(id);
    }



    @DeleteMapping(value = ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<User> deleteUser(@PathVariable("id") Integer id) {
        //Retornando 409 apos deletar usuario
        return userService.deleteUser(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<List<UserDTO>> findAll(Pageable paginacao) {
        return userService.findAll(paginacao);
    }


    @GetMapping(value = ID)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<User> findById(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }

    @GetMapping(value = NAME)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<List<UserDTO>> findByName(@RequestParam("name") String name) {
        return userService.findByName(name);
    }


    @GetMapping(value = CPF)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<User> findByCPF(@PathVariable("cpf") String cpf) {
        return userService.findByCpf(cpf);
    }


    @GetMapping(value = EMAIL)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<UserDTO> findByEmail(@PathVariable("email") String email) {
        return userService.findByEmail(email);
    }


}
