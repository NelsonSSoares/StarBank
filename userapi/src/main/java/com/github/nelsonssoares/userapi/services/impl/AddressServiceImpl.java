package com.github.nelsonssoares.userapi.services.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nelsonssoares.userapi.domain.dtos.AddressDTO;
import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.domain.entities.Address;
import com.github.nelsonssoares.userapi.outlayers.entrypoints.AddressController;
import com.github.nelsonssoares.userapi.outlayers.entrypoints.UserController;
import com.github.nelsonssoares.userapi.usecases.address.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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
        addHateoasLinks(endereco);
        return endereco != null ? ResponseEntity.status(HttpStatus.CREATED).body(endereco) : ResponseEntity.badRequest().build();
    }

    @Override
    public ResponseEntity<Address> update(Integer id, AddressDTO endDto) {
        Address endereco = updateAddress.executeUpdateAddress(id, endDto);
        addHateoasLinks(endereco);
        return endereco != null ? ResponseEntity.ok(endereco) : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Address> delete(Integer id) {
        Address endereco = deleteAddress.executeDeteleAddress(id);
        if (endereco != null) {
            addHateoasLinks(endereco);
        }
        return endereco != null ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<Address> findById(Integer id) {
        Address endereco = getAddressById.executeAddressById(id);
        addHateoasLinks(endereco);
        return endereco != null ? ResponseEntity.ok(endereco) : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<Address>> findAll(Pageable paginacao) {
        List<Address> enderecos = getAllAddresses.executeAllAddresses(paginacao);
        for (Address endereco : enderecos) {
            addHateoasLinks(endereco);
        }
        return enderecos != null ? ResponseEntity.ok(enderecos) : ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<List<Address>> findByUserId(Integer id) {
        List<Address> enderecos = getAddressByUserId.executeAddressByUserId(id);
        for (Address endereco : enderecos) {
            addHateoasLinks(endereco);
        }
        return enderecos != null ? ResponseEntity.ok(enderecos) : ResponseEntity.notFound().build();

    }

    private static void addHateoasLinks(Address adrss) {
        ObjectMapper objectMapper = new ObjectMapper();
        AddressDTO dto = objectMapper.convertValue(adrss, AddressDTO.class);
        dto.add(linkTo(methodOn(AddressController.class).findById(adrss.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(AddressController.class).delete(adrss.getId())).withRel("delete").withType("DELETE"));
        dto.add(linkTo(methodOn(AddressController.class).findAllAddresses(PageRequest.of(0, 10))).withRel("findAll").withType("GET"));
        dto.add(linkTo(methodOn(AddressController.class).save(dto)).withRel("save").withType("POST"));
        dto.add(linkTo(methodOn(AddressController.class).updateAddress(adrss.getId(), dto)).withRel("updateAddress").withType("PUT"));
        dto.add(linkTo(methodOn(AddressController.class).findAddressByUserId(adrss.getUserId())).withRel("findAddressByUserId").withType("GET"));

    }
}
