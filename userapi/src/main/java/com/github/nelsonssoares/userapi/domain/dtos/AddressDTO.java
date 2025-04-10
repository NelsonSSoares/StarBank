package com.github.nelsonssoares.userapi.domain.dtos;


import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO extends RepresentationModel<AddressDTO> implements Serializable {
    private static final long serialVersionUID = 1L;
    Long id;
    String nickname;
    String street;
    String number;
    String complement;
    String zipCode;
    String neighborhood;
    String city;
    String state;
    Integer userId;
    String country;
}
