package com.github.nelsonssoares.userapi.domain.repositories;

import com.github.nelsonssoares.userapi.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "select * from usuario u where u.cpf = :cpf", nativeQuery = true)
    Optional<User> findByCpf(@Param("cpf") String cpf);

    @Query(value = "select * from usuario u where u.nome like :nome%", nativeQuery = true)
    List<User> findByNome(@Param("nome") String nome);

    @Query(value = "select * from usuario u where u.email = :email", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);
}
