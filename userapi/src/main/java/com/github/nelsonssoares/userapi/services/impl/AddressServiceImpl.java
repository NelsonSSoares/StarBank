package com.github.nelsonssoares.userapi.services.impl;

import com.github.nelsonssoares.userapi.domain.dtos.AddressDTO;
import com.github.nelsonssoares.userapi.domain.entities.Address;
import com.github.nelsonssoares.userapi.usecases.address.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements com.github.nelsonssoares.userapi.services.AddressService {

    private final SaveAddress saveAddress;
    private final GetAllAddresses getAllAddresses;
    private final GetAddressByUserId getAddressByUserId;
    private final UpdateAddress updateAddress;
    private final DeleteAddress deleteAddress;
    private final GetAddressById getAddressById;

    @Override
    public ResponseEntity<Address> save(AddressDTO endDto) {
        Address endereco = saveAddress.executeSaveAddress(endDto);
        return endereco != null ? ResponseEntity.status(HttpStatus.CREATED).body(endereco) : ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<Address> update(Integer id, AddressDTO endDto) {
        Address endereco = updateAddress.executeUpdateAddress(id, endDto);
        return endereco != null ? ResponseEntity.ok(endereco) : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Address> delete(Integer id) {
        Address endereco = deleteAddress.executeDeteleAddress(id);
        return endereco != null ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Address> findById(Integer id) {
        Address endereco = getAddressById.executeAddressById(id);
        return endereco != null ? ResponseEntity.ok(endereco) : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<Address>> findAll(Pageable paginacao) {
        List<Address> enderecos = getAllAddresses.executeAllAddresses(paginacao);
        return enderecos != null ? ResponseEntity.ok(enderecos) : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<Address>> findByUserId(Integer id) {
        List<Address> enderecos = getAddressByUserId.executeAddressByUserId(id);
        return enderecos != null ? ResponseEntity.ok(enderecos) : ResponseEntity.notFound().build();

    }
}
