package com.github.nelsonssoares.userapi.usecases.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SaveUserPhoto {
    private final GetUserById getUserById;
    private final UpdateUser updateUser;
    private final ObjectMapper mapper;

    public String executeSaveUserPhoto(Integer id, MultipartFile file) {
        var user = getUserById.executeUserById(id);

        if (user == null) {
            throw new IllegalArgumentException("Usuário não encontrado.");
        }

        String uploadDir = "uploads/";
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);

        try {
            Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar a foto.", e);
        }

        // Atualizar o nome da foto no usuário
        user.setPhoto(fileName);

        UserDTO userDTO = mapper.convertValue(user, UserDTO.class);

        // Atualizar o usuário
        updateUser.executeUpdateUser(id, userDTO);

        return fileName;
    }
}