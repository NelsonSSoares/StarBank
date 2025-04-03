package com.github.nelsonssoares.userapi.outlayers.entrypoints;

import com.github.nelsonssoares.userapi.domain.dtos.AddressDTO;
import com.github.nelsonssoares.userapi.domain.entities.Address;
import com.github.nelsonssoares.userapi.services.AddressService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.nelsonssoares.userapi.commons.constants.controllers.ControllersConstants.*;


@Tag(name = API_TAG_ADRESS, description = API_DESCRIPTION)
@RequiredArgsConstructor
@RestController
@RequestMapping(value = API_BASE_URL+ADDRESS, produces = API_PRODUCES)
public class AddressController implements com.github.nelsonssoares.userapi.outlayers.entrypoints.docs.AddressControllerDoc {

    private final AddressService addressService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<Address> save(@RequestBody @Valid AddressDTO enderecoDTO) {
        return addressService.save(enderecoDTO);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<List<Address>> findAllAddresses(Pageable paginacao) {
        return addressService.findAll(paginacao);
    }


    @GetMapping(value = ADDRESS_USER_ID)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<List<Address>> findAddressByUserId(@PathVariable("id") Integer id) {
        return addressService.findByUserId(id);
    }


    @PutMapping(value = ID)
    @ResponseStatus(HttpStatus.CREATED)
    @Override
    public ResponseEntity<Address> updateAddress(@PathVariable("id") Integer id, @RequestBody @Valid AddressDTO enderecoDTO) {
        return addressService.update(id, enderecoDTO);
    }


    @DeleteMapping(value = ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Override
    public ResponseEntity<Address> delete(@PathVariable("id") Integer id) {
        return addressService.delete(id);
    }


    @GetMapping(value = ID)
    @ResponseStatus(HttpStatus.OK)
    @Override
    public ResponseEntity<Address> findById(@PathVariable("id") Integer id) {
        return addressService.findById(id);
    }

}
