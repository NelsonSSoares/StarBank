package com.github.nelsonssoares.userapi.usecases.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelsonssoares.userapi.commons.constants.enums.Countries;
import com.github.nelsonssoares.userapi.domain.dtos.AddressDTO;
import com.github.nelsonssoares.userapi.domain.entities.Address;
import com.github.nelsonssoares.userapi.domain.entities.User;
import com.github.nelsonssoares.userapi.domain.repositories.AddressRepository;
import com.github.nelsonssoares.userapi.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaveAddress {

    private final AddressRepository addressRepository;
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    @Transactional
    public Address executeSaveAddress(AddressDTO addressDTO) {

        Address address = objectMapper.convertValue(addressDTO, Address.class);
        Optional<User> usuarioId = userRepository.findById(addressDTO.getUserId());
        if(usuarioId.isPresent()){
            String pais = addressDTO.getCountry().toUpperCase();
            Countries contryConverted = Countries.valueOf(pais);
            address.setCountry(contryConverted);
            address.setUserId(usuarioId.get().getId());
            addressRepository.save(address);
            return address;
        }else {
           return null;
        }

    }
}
