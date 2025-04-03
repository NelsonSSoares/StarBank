package com.github.nelsonssoares.userapi.usecases.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelsonssoares.userapi.commons.constants.enums.UserActive;
import com.github.nelsonssoares.userapi.commons.constraints.Constraints;
import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.domain.entities.User;
import com.github.nelsonssoares.userapi.domain.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SaveUser {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public UserDTO executeSaveUser(UserDTO user) {

        List<User> usuarios = userRepository.findAll();

        boolean result = Constraints.ExistentCPGF(usuarios, user);
        System.out.println(result);

        if(result == true) {
            throw new RuntimeException("CPF Já cadastrado! " + HttpStatus.CONFLICT);
        }

        User usuario = objectMapper.convertValue(user, User.class);

        usuario.setActive(UserActive.ACTIVE);
        usuario.setCreationDate(LocalDate.now());
        usuario.setModificationDate(LocalDate.now());
        usuario.setAgency("001");
        usuario.setAccount(generateRandomAccountNumber());

        User userSaved = userRepository.save(usuario);

        return objectMapper.convertValue(userSaved, UserDTO.class);

    }
    private String generateRandomAccountNumber() {
        Random random = new Random();
        int accountNumber = 100000 + random.nextInt(900000); // Gera um número de 6 dígitos
        return String.valueOf(accountNumber);
    }
}
