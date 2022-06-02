package com.proxiad.restGameService.services.implementations;

import com.proxiad.restGameService.dtos.RegistrationDTO;
import com.proxiad.restGameService.dtos.UserDTO;
import com.proxiad.restGameService.entities.User;
import com.proxiad.restGameService.repositories.UserRepository;
import com.proxiad.restGameService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

public class UserServiceImpl implements UserService {
    private UserRepository repository;
    private PasswordEncoder encoder;

    @Override
    public User getByUsername(String username){
        return repository.findByUsername(username).get();
        //TODO implement custom exception
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> byUsername;
        if (username.contains("@")) {
            byUsername = repository.findByEmail(username);
        } else
            byUsername = repository.findByUsername(username);
        byUsername.orElseThrow(() -> new UsernameNotFoundException("Not found: " + username));
        return byUsername.get();
    }

    @Override
    public User registerNewUser(RegistrationDTO toRegister) {
        if (repository.findByEmail(toRegister.getEmail()).isPresent() || repository.findByUsername(toRegister.getUsername()).isPresent()) {
            //todo implement custom exception
        }
        User user = new User(
                toRegister.getUsername(),
                encoder.encode(toRegister.getPassword()),
                toRegister.getEmail(), toRegister.getFirstName(), toRegister.getLastName());
        return repository.save(user);
    }

    @Autowired
    public UserServiceImpl(UserRepository repository, @Lazy PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }
}
