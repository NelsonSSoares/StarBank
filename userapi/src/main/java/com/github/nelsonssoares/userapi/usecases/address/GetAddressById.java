package com.github.nelsonssoares.userapi.usecases.address;

import com.github.nelsonssoares.userapi.commons.constants.enums.UserActive;
import com.github.nelsonssoares.userapi.domain.entities.Address;
import com.github.nelsonssoares.userapi.domain.entities.User;
import com.github.nelsonssoares.userapi.domain.repositories.AddressRepository;
import com.github.nelsonssoares.userapi.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetAddressById {
    private final AddressRepository addressRepository;
    private final UserRepository usuarioRepository;

    public Address executeAddressById(Integer id) {
        Optional<Address> address = addressRepository.findById(id);
        if (address.isEmpty()) {
            return null;
        }
        Optional<User> usuario = usuarioRepository.findById(address.get().getUserId());
        if(usuario.get().getActive().equals(UserActive.INACTIVE)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado!");
        }
        return address.get();

    }

}
