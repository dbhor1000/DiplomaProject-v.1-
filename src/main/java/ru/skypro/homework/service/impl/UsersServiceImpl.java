package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CurrentUser;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.service.UsersService;


@Service
public class UsersServiceImpl implements UsersService {

    private final UserDetailsManager manager;
    private final PasswordEncoder encoder;

    public UsersServiceImpl(UserDetailsManager manager,
                           PasswordEncoder passwordEncoder) {
        this.manager = manager;
        this.encoder = passwordEncoder;
    }

    @Override
    public boolean changePassword(String oldPassword, String newPassword) {
        if (oldPassword.equals(newPassword)) {
            return false;
        } else {

            manager.changePassword(oldPassword, newPassword);
            return true;
        }
    }

    @Override
    public User getUserInfo() {
        if(AuthServiceImpl.userLog.isEmpty()){
            return false;
        } else {

            return true;
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.getName();
        User currentUser = manager.loadUserByUsername(currentUserName);

    }


}
