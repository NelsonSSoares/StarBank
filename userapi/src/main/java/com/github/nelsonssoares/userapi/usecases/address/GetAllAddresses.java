package com.github.nelsonssoares.userapi.usecases.address;

import com.github.nelsonssoares.userapi.domain.entities.Address;
import com.github.nelsonssoares.userapi.domain.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllAddresses {

    private final AddressRepository addressRepository;


    public List<Address> executeAllAddresses(Pageable paginacao) {

        return addressRepository.findActiveAddresses(paginacao);

    }
}
