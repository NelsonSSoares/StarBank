package com.github.nelsonssoares.userapi.usecases.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelsonssoares.userapi.commons.constants.enums.UserActive;
import com.github.nelsonssoares.userapi.domain.dtos.AddressDTO;
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
public class UpdateAddress {

    private final UserRepository usuarioRepository;
    private final AddressRepository enderecoRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public Address executeUpdateAddress(Integer id, AddressDTO endDto) {
        Optional<Address> endereco = enderecoRepository.findById(id);

        if(endereco.isEmpty() ){
            return null;
        }
        Address adress = endereco.get();
        Optional<User> usuario = usuarioRepository.findById(adress.getUserId());

        if(usuario.get().getActive().equals(UserActive.INACTIVE) || usuario.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        }

        Address enderecoAtualizado = objectMapper.convertValue(endDto, Address.class);
        enderecoAtualizado.setId(id);
        enderecoAtualizado.setUserId(adress.getUserId());
        enderecoRepository.save(enderecoAtualizado);
        return enderecoAtualizado;
    }
}
