package com.github.nelsonssoares.userapi.usecases.address;

import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomuserapi.domain.entities.Endereco;
import nelsonssoares.ecomuserapi.domain.entities.Usuario;
import nelsonssoares.ecomuserapi.domain.entities.enums.PerguntaAtivo;
import nelsonssoares.ecomuserapi.domain.repository.EnderecoRepository;
import nelsonssoares.ecomuserapi.domain.repository.UsuarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetAddressById {
    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    public Endereco executeAddressById(Integer id) {
        Optional<Endereco> endereco = enderecoRepository.findById(id);
        if (endereco.isEmpty()) {
            return null;
        }
        Optional<Usuario> usuario = usuarioRepository.findById(endereco.get().getUsuarioId());
        if(usuario.get().getAtivo().equals(PerguntaAtivo.NAO)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Endereço não encontrado!");
        }
        return endereco.get();

    }

}
