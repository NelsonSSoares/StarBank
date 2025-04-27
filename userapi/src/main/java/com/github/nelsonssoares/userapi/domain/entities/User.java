package com.github.nelsonssoares.userapi.domain.entities;

import com.github.nelsonssoares.userapi.commons.constants.enums.UserActive;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"cpf", "email"})})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 50)
    @NotEmpty(message = "Este campo não pode ser nulou ou vazio, deve conter de 2 até 50 caracteres.")
    private String name;

    @Column(length = 50)
    @NotEmpty(message = "Este campo não pode ser nulou ou vazio, deve conter de 2 até 50 caracteres.")
    private String lastName;

    @Column(length = 14)
    @NotEmpty(message = "Informe CPF Válido! EX: 123.456.789-10")
    //@CPF(message = "Informe CPF Válido! EX: 123.456.789-10")
    private String cpf;

    @Column(length = 20)
//    @Pattern(regexp =
//     "(^(?:(?:\\+|00)?(55)\\s?)?(?:\\(?([1-9][0-9])\\)?\\s?)?(?:((?:9\\d|[2-9])\\d{3})\\-?(\\d{4}))$)",
//            message = "Telefone Inválido")
    private String phone;

    @Email
    @NotEmpty(message = "Informe um e-mail válido! Ex: 123deoliveira4@gmail.com")
    @Column(length = 90)
    private String email;

    @DateTimeFormat(pattern = "YYYY-MM-DD")
    @Column(name = "creation_date")
    @NotNull
    private LocalDate creationDate;

    @DateTimeFormat(pattern = "YYYY-MM-DD")
    @Column(name = "modification_date")
    @NotNull
    private LocalDate modificationDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private UserActive active;

    @NotNull
    private String account;

    @NotNull
    private String agency;

    private String photo;



}
