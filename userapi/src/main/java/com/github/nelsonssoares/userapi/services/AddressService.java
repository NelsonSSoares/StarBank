package com.github.nelsonssoares.userapi.services;

import com.github.nelsonssoares.userapi.domain.dtos.AddressDTO;
import com.github.nelsonssoares.userapi.domain.entities.Address;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AddressService {
    ResponseEntity<Address> save(AddressDTO endDto);

    ResponseEntity<Address> update(Integer id, AddressDTO endDto);

    ResponseEntity<Address> delete(Integer id);

    ResponseEntity<Address> findById(Integer id);

    ResponseEntity<List<Address>> findAll(Pageable paginacao);

    ResponseEntity<List<Address>> findByUserId(Integer id);
}
