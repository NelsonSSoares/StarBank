package com.github.nelsonssoares.userapi.outlayers.entrypoints.docs;

import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.domain.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.nelsonssoares.userapi.commons.constants.controllers.ControllersConstants.*;

public interface UserControllerDoc {
    @Operation(summary = "Metodo para cadastrar novo usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário cadstrado com sucesso!!"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos!"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!"),
            @ApiResponse(responseCode = "403", description = "Não Autorizado!"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválido"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar usuário!"),
    })
    ResponseEntity<UserDTO> save(@RequestBody @Valid UserDTO dto);

    @Operation(summary = "Metodo para atualizar usuário existente", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário atualizado com sucesso!!"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos!"),
            @ApiResponse(responseCode = "403", description = "Não Autorizado!"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválido"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar usuário!"),
    })
    ResponseEntity<User> updateUser(@PathVariable("id") Integer id, @RequestBody @Valid UserDTO userDTO);

    @Operation(summary = "Metodo para reativar usuário", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário reativado com sucesso!!"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!"),
            @ApiResponse(responseCode = "403", description = "Não Autorizado!"),
            @ApiResponse(responseCode = "409", description = "Usuário já ativo!"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar usuário!"),
    })
    ResponseEntity<User> activeUser(@PathVariable("id") Integer id);

    @Operation(summary = "Metodo para excluir usuário existente", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário excluido com sucesso!!"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos!"),
            @ApiResponse(responseCode = "403", description = "Não Autorizado!"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!"),
    })
    ResponseEntity<User> deleteUser(@PathVariable("id") Integer id);

    @Operation(summary = "Busca todos os Usuários cadastrados", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado!"),
            @ApiResponse(responseCode = "403", description = "Não Autorizado!"),
            @ApiResponse(responseCode = "500", description = "Erro ao buscar usuário!")
    })
    ResponseEntity<List<UserDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction
    );

    @Operation(summary = "Busca usuario cadastrado por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado!"),
            @ApiResponse(responseCode = "403", description = "Não Autorizado!"),
            @ApiResponse(responseCode = "500", description = "Erro ao buscar usuário!")
    })
    ResponseEntity<User> findById(@PathVariable("id") Integer id);

    @Operation(summary = "Busca usuarios cadastrados por Nome", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado!"),
            @ApiResponse(responseCode = "403", description = "Não Autorizado!"),
            @ApiResponse(responseCode = "500", description = "Erro ao buscar usuário!")
    })
    ResponseEntity<List<UserDTO>> findByName(@RequestParam("nome") String nome);

    @Operation(summary = "Busca usuario cadastrado por CPF", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado!"),
            @ApiResponse(responseCode = "403", description = "Não Autorizado!"),
            @ApiResponse(responseCode = "500", description = "Erro ao buscar usuário!")
    })
    ResponseEntity<User> findByCPF(@PathVariable("cpf") String cpf);

    @Operation(summary = "Busca usuario cadastrado por Email", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Busca realizada com sucesso!"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado!"),
            @ApiResponse(responseCode = "403", description = "Não Autorizado!"),
            @ApiResponse(responseCode = "500", description = "Erro ao buscar usuário!")
    })
    ResponseEntity<UserDTO> findByEmail(@PathVariable("email") String email);
}
