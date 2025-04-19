package com.github.nelsonssoares.userapi.domain.dtos;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "users")
public class UserDTO  extends RepresentationModel<UserDTO> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    public String name;
    public String lastName;
    public String cpf;
    public String phone;
    public String email;
    public String account;
    public String agency;
}
