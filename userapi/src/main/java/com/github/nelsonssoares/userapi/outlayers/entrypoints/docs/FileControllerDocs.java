package com.github.nelsonssoares.userapi.outlayers.entrypoints.docs;

import com.github.nelsonssoares.userapi.domain.dtos.UploadFileResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileControllerDocs {

    @Operation(summary = "Metodo para salvar foto de usuário", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Foto salva com sucesso!!"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos!"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!"),
            @ApiResponse(responseCode = "403", description = "Não Autorizado!"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválido"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar usuário!"),
    })
    UploadFileResponseDTO uploadFile(MultipartFile files, Integer id) throws IOException;

    @Operation(summary = "Metodo para baixar foto de usuário", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Download feito com sucesso!!"),
            @ApiResponse(responseCode = "400", description = "Parametros inválidos!"),
            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!"),
            @ApiResponse(responseCode = "403", description = "Não Autorizado!"),
            @ApiResponse(responseCode = "422", description = "Dados de requisição inválido"),
            @ApiResponse(responseCode = "500", description = "Erro ao atualizar usuário!"),
    })
    ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request) throws IOException;

    //    @Operation(summary = "Metodo para cadastrar novo usuário", method = "POST")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Usuário cadstrado com sucesso!!"),
//            @ApiResponse(responseCode = "400", description = "Parametros inválidos!"),
//            @ApiResponse(responseCode = "401", description = "Usuário não autenticado!"),
//            @ApiResponse(responseCode = "403", description = "Não Autorizado!"),
//            @ApiResponse(responseCode = "422", description = "Dados de requisição inválido"),
//            @ApiResponse(responseCode = "500", description = "Erro ao atualizar usuário!"),
//    })
//    List<UploadFileResponseDTO> uploadMultipleFiles(MultipartFile[] file);

}
