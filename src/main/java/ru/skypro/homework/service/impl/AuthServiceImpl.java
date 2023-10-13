package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.CustomUserDetailsService;

import java.util.ArrayList;


@Service
public class AuthServiceImpl implements AuthService {

    //private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserDetailsService customUserDetailsService;
    private final UserRepository userRepository;

    static ArrayList<String> userLog = new ArrayList<>();

    //public AuthServiceImpl(UserDetailsManager manager, PasswordEncoder encoder, UserRepository userRepository) {
    //    this.manager = manager;
    //    this.encoder = encoder;
    //    this.userRepository = userRepository;
    //}


    //public AuthServiceImpl(UserDetailsManager manager, PasswordEncoder encoder, CustomUserDetailsService customUserDetailsService, UserRepository userRepository) {
    //    this.manager = manager;
    //    this.encoder = encoder;
    //    this.customUserDetailsService = customUserDetailsService;
    //    this.userRepository = userRepository;
    //}

    public AuthServiceImpl(PasswordEncoder encoder, UserDetailsService customUserDetailsService, UserRepository userRepository) {
        this.encoder = encoder;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    public boolean login(String userName, String password) {
        //if (!manager.userExists(userName)) {
        if (userRepository.findByUsername(userName) == null) {
            return false;
        } else {
            //UserDetails userDetails = manager.loadUserByUsername(userName);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
            userLog.add(userName);
            return encoder.matches(password, encoder.encode(userDetails.getPassword())); //Тут немного не уверен. ???
            //return (password.equals(userDetails.getPassword()));
        }
    }

    //@Override
    //public boolean register(Register register) {
    //    if (manager.userExists(register.getUsername())) {
    //        return false;
    //    }
    //    manager.createUser(
    //            User.builder()
    //                    .passwordEncoder(this.encoder::encode)
    //                    .password(register.getPassword())
    //                    .username(register.getUsername())
    //                    .roles(register.getRole().name())
    //                    .build());
    //    return true;
    //}

}
