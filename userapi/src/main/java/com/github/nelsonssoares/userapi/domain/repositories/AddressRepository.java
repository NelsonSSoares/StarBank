package com.github.nelsonssoares.userapi.domain.repositories;

import com.github.nelsonssoares.userapi.domain.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
