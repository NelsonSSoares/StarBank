package com.github.nelsonssoares.userapi.domain.dtos;

public record UserDTO(
        String name,
        String lastName,
        String cpf,
        String phone,
        String email,
        String account,
        String agency
) {
}
