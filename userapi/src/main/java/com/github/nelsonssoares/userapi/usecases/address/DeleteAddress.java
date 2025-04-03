package com.github.nelsonssoares.userapi.usecases.address;

import lombok.RequiredArgsConstructor;
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
public class DeleteAddress {
    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;

    @Transactional
    public Endereco executeDeteleAddress(Integer id) {
        //metodo deve retornar apenas 204

        Optional<Endereco> endereco = enderecoRepository.findById(id);
        if (endereco.isEmpty()) {
            return null;
        }
        Endereco adress = endereco.get();
        Optional<Usuario> usuario = usuarioRepository.findById(adress.getUsuarioId());
        if (usuario.get().getAtivo().equals(PerguntaAtivo.NAO) || usuario.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado!");
        }
        enderecoRepository.deleteById(id);
        return adress;
    }
}
