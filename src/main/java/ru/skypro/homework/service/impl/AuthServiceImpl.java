package ru.skypro.homework.service.impl;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.mapping.UsersMapping;


@Service
public class AuthServiceImpl implements AuthService {

    //private final UserDetailsManager manager;
    private final PasswordEncoder encoder;
    private final UserDetailsService customUserDetailsService;
    private final UserRepository userRepository;
    private final UsersMapping usersMapping;


    public AuthServiceImpl(PasswordEncoder encoder, UserDetailsService customUserDetailsService, UserRepository userRepository, UsersMapping usersMapping) {
        this.encoder = encoder;
        this.customUserDetailsService = customUserDetailsService;
        this.userRepository = userRepository;
        this.usersMapping = usersMapping;
    }

    /**
     * Сервисный метод для входа в систему по login и password.
     * @param userName имя пользователя, password пароль пользователя
     * @return boolean true/false
     */

    @Override
    public boolean login(String userName, String password) {
        //if (!manager.userExists(userName)) {
        if (userRepository.findByUsername(userName) == null) {
            return false;
        } else {
            //UserDetails userDetails = manager.loadUserByUsername(userName);
            UserDetails userDetails = customUserDetailsService.loadUserByUsername(userName);
            return encoder.matches(password, userDetails.getPassword()); //Тут немного не уверен. ???
            //return (password.equals(userDetails.getPassword()));
        }
    }

    /**
     * Сервисный метод для регистрации нового пользователя в системе.
     * @param register DTO-object с данными новго пользователя
     * @return boolean true/false
     */

    @Override
    public boolean register(Register register){
        if (userRepository.findByUsername(register.getUsername()) == null) {
            UserEntity convertedFromDTO = usersMapping.registerDTOtoUserEntity(register);
            convertedFromDTO.setPassword(encoder.encode(register.getPassword()));
            userRepository.save(convertedFromDTO);
            return true;
        }

        return false;
    }

}
