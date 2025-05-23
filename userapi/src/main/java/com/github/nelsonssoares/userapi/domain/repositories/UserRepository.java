package com.github.nelsonssoares.userapi.domain.repositories;

import com.github.nelsonssoares.userapi.domain.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from user u where u.cpf = :cpf", nativeQuery = true)
    Optional<User> findByCpf(@Param("cpf") String cpf);

    @Query("SELECT u FROM User u WHERE u.name LIKE LOWER(CONCAT ('%', :name, '%'))")
    Page<User> findByNome(@Param("name") String nome, Pageable pageable);

    @Query(value = "select * from user u where u.email = :email", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);
}
