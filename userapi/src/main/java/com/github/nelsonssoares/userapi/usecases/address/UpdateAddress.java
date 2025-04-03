package com.github.nelsonssoares.userapi.usecases.address;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomuserapi.domain.dtos.EnderecoDTO;
import nelsonssoares.ecomuserapi.domain.entities.Endereco;
import nelsonssoares.ecomuserapi.domain.entities.Usuario;
import nelsonssoares.ecomuserapi.domain.entities.enums.PerguntaAtivo;
import nelsonssoares.ecomuserapi.domain.repository.EnderecoRepository;
import nelsonssoares.ecomuserapi.domain.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UpdateAddress {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public Endereco executeUpdateAddress(Integer id, EnderecoDTO endDto) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);

        if(endereco.isEmpty() ){
            return null;
        }
        Endereco adress = endereco.get();
        Optional<Usuario> usuario = usuarioRepository.findById(adress.getUsuarioId());

        if(usuario.get().getAtivo().equals(PerguntaAtivo.NAO) || usuario.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        }

        Endereco enderecoAtualizado = objectMapper.convertValue(endDto, Endereco.class);
        enderecoAtualizado.setId(id);
        enderecoAtualizado.setEnderecoPadrao(endDto.enderecoPadrao());
        enderecoAtualizado.setUsuarioId(adress.getUsuarioId());
        enderecoRepository.save(enderecoAtualizado);
        return enderecoAtualizado;
    }
}
