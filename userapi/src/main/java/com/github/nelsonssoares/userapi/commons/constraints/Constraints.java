package com.github.nelsonssoares.userapi.commons.constraints;

import com.github.nelsonssoares.userapi.commons.constants.enums.UserActive;
import com.github.nelsonssoares.userapi.domain.dtos.UserDTO;
import com.github.nelsonssoares.userapi.domain.entities.User;
import org.springframework.data.domain.Page;

import java.util.Iterator;
import java.util.List;

public class Constraints {

    public static Page<User> usersActivePage(Page<User> usuarios) {
        Iterator<User> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            User usuario = iterator.next();
            if (usuario.getActive().equals(UserActive.INACTIVE)) {
                iterator.remove();
            }
        }
        return usuarios;
    }

    public static boolean ExistentCPGF(List<User> usuarios, UserDTO usuario){

        Iterator<User> iterator = usuarios.iterator();
        while(iterator.hasNext()) {
            User user = iterator.next();
            if(user.getCpf().equals(usuario.cpf())) {
                return true;
            }
        }
        return false;
    }

    public static List<User> usuariosAtivosList(List<User> usuarios) {
        Iterator<User> iterator = usuarios.iterator();
        while (iterator.hasNext()) {
            User usuario = iterator.next();
            if (usuario.getActive().equals(UserActive.INACTIVE)) {
                iterator.remove();
            }
        }
        return usuarios;
    }


}
