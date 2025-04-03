package com.github.nelsonssoares.userapi.usecases.address;

import lombok.RequiredArgsConstructor;
import nelsonssoares.ecomuserapi.domain.entities.Endereco;
import nelsonssoares.ecomuserapi.domain.repository.EnderecoRepository;
import nelsonssoares.ecomuserapi.domain.repository.UsuarioRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllAddresses {

    private final EnderecoRepository enderecoRepository;
    private final UsuarioRepository usuarioRepository;

    public List<Endereco> executeAllAddresses(Pageable paginacao) {

        return enderecoRepository.findActiveAddresses(paginacao);

    }
}
