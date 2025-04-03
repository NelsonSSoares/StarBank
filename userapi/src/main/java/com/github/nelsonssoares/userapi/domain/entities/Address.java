package com.github.nelsonssoares.userapi.domain.entities;

import com.github.nelsonssoares.userapi.commons.constants.enums.Countries;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 45)
    private String nickname;

    @NotEmpty(message = "Este campo não pode ser nulou ou vazio, deve conter de 2 até 45 caracteres.")
    @Column(length = 45)
    private String street;

    @NotEmpty(message = "Este campo não pode ser nulou ou vazio, deve conter de 2 até 45 caracteres.")
    @Column(length = 45)
    private String number;

    @NotEmpty(message = "Este campo não pode ser nulou ou vazio, deve conter de 2 até 45 caracteres.")
    @Column(length = 45)
    private String complement;

    @NotEmpty(message = "Este campo não pode ser nulou ou vazio, deve conter de 2 até 45 caracteres.")
    @Column(length = 45)
    private String zipCode;

    @NotEmpty(message = "Este campo não pode ser nulou ou vazio, deve conter de 2 até 45 caracteres.")
    @Column(length = 45)
    private String neighborhood;

    @NotEmpty(message = "Este campo não pode ser nulou ou vazio, deve conter de 2 até 45 caracteres.")
    @Column(length = 45)
    private String city;

    @NotEmpty(message = "Deve conter somente 2 caracteres ex: SP")
    @Column(length = 2)
    private String state;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Countries country;


    private Integer userId;


}
