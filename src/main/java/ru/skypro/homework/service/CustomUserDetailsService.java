package ru.skypro.homework.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.ExtendedLoginViaDB;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.mapping.UsersMapping;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    private final UserRepository userRepository;
    private final UsersMapping usersMapping;

    public CustomUserDetailsService(UserRepository userRepository, UsersMapping usersMapping) {
        this.userRepository = userRepository;
        this.usersMapping = usersMapping;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {

        UserEntity userEntity = userRepository.findByUsername(username);
        ExtendedLoginViaDB userEntityDTO = usersMapping.extendedLoginViaDB(userEntity);
        if (userEntity == null) {
            throw new UserNotFoundException();
        }

        return new CustomUserDetails(userEntityDTO);

    }

}
