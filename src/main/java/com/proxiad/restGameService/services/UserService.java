package com.proxiad.restGameService.services;

import com.proxiad.restGameService.dtos.RegistrationDTO;
import com.proxiad.restGameService.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User getByUsername(String username);
    User registerNewUser(RegistrationDTO toRegister);
}
