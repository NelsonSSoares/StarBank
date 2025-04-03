package com.github.nelsonssoares.userapi.domain.repositories;

import com.github.nelsonssoares.userapi.domain.entities.Address;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    List<Address> findAllByUserId(Integer id);

    @Query(value = "SELECT e.* FROM address a JOIN user u ON a.user_id = u.id WHERE u.active = 'ACTIVE'", nativeQuery = true)
    List<Address> findActiveAddresses(Pageable paginacao);

}
