package com.github.nelsonssoares.userapi.domain.dtos.response;

import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Relation(collectionRelation = "userResponse")
public class UserResponseDTO extends RepresentationModel<UserResponseDTO> implements Serializable {

    private static final long serialVersionUID = 1L;
    private Integer id;
    public String name;
    public String lastName;
    public String cpf;
    public String phone;
    public String email;
    public String photo;
    public String agency;
    public String account;
}
