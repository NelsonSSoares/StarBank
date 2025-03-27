package com.github.nelsonssoares.userapi.domain.dtos;

public record AddressDTO(
        Long id,
        String nickname,
        String street,
        String number,
        String complement,
        String zipCode,
        String neighborhood,
        String city,
        String state
) {
}
