package com.github.nelsonssoares.userapi.usecases.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomuserapi.domain.dtos.EnderecoDTO;
import nelsonssoares.ecomuserapi.domain.entities.Endereco;
import nelsonssoares.ecomuserapi.domain.entities.Usuario;
import nelsonssoares.ecomuserapi.domain.entities.enums.Pais;
import nelsonssoares.ecomuserapi.domain.repository.EnderecoRepository;
import nelsonssoares.ecomuserapi.domain.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SaveAddress {

    private final EnderecoRepository enderecoRepository;
    private final ObjectMapper objectMapper;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public Endereco executeSaveAddress(EnderecoDTO enderecoDTO) {

        Endereco endereco = objectMapper.convertValue(enderecoDTO, Endereco.class);
        Optional<Usuario> usuarioId = usuarioRepository.findById(enderecoDTO.usuarioId());
        if(usuarioId.isPresent()){
            String pais = enderecoDTO.pais().toUpperCase();
            Pais paisConverted = Pais.valueOf(pais);
            endereco.setPais(paisConverted);
            endereco.setUsuarioId(usuarioId.get().getId());
            enderecoRepository.save(endereco);
            return endereco;
        }else {
           return null;
        }

    }
}
