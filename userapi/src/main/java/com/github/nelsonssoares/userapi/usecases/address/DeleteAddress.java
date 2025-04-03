package com.github.nelsonssoares.userapi.usecases.address;

import com.github.nelsonssoares.userapi.commons.constants.enums.UserActive;
import com.github.nelsonssoares.userapi.domain.entities.Address;
import com.github.nelsonssoares.userapi.domain.entities.User;
import com.github.nelsonssoares.userapi.domain.repositories.AddressRepository;
import com.github.nelsonssoares.userapi.domain.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DeleteAddress {
    private final UserRepository userRepository;
    private final AddressRepository adressRepository;

    @Transactional
    public Address executeDeteleAddress(Integer id) {
        //metodo deve retornar apenas 204

        Optional<Address> adress = adressRepository.findById(id);
        if (adress.isEmpty()) {
            return null;
        }
        Address adressopt = adress.get();
        Optional<User> user = userRepository.findById(adressopt.getUserId());
        if (user.get().getActive().equals(UserActive.INACTIVE) || user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        }
        adressRepository.deleteById(id);
        return adressopt;
    }
}
