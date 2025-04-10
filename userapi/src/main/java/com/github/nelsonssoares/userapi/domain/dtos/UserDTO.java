package com.github.nelsonssoares.userapi.domain.dtos;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO  extends RepresentationModel<UserDTO> implements Serializable {
    public String name;
    public String lastName;
    public String cpf;
    public String phone;
    public String email;
    public String account;
    public String agency;
}
