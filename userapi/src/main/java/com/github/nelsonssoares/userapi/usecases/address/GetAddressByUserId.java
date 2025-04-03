package com.github.nelsonssoares.userapi.usecases.address;


import com.github.nelsonssoares.userapi.domain.entities.Address;
import com.github.nelsonssoares.userapi.domain.entities.User;
import com.github.nelsonssoares.userapi.domain.repositories.AddressRepository;
import com.github.nelsonssoares.userapi.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetAddressByUserId {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public List<Address> executeAddressByUserId(Integer id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        List<Address> addresses = addressRepository.findAllByUserId(id);
        if (addresses.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado");
        }

        return addresses;
    }
}
