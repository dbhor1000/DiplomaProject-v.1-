package ru.skypro.homework.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UsersService;
import ru.skypro.homework.service.mapping.UsersMapping;


@Service
public class UsersServiceImpl implements UsersService {

    //_
    //                        _ooOoo_
    //                       o8888888o
    //                       88" . "88
    //                       (| -_- |)
    //                       O\  =  /O
    //                    ____/`---'\____
    //                  .'  \\|     |//  `.
    //                 /  \\|||  :  |||//  \
    //                /  _||||| -:- |||||_  \
    //                |   | \\\  -  /'| |   |
    //                | \_|  `\`---'//  |_/ |
    //                \  .-\__ `-. -'__/-.  /
    //              ___`. .'  /--.--\  `. .'___
    //           ."" '<  `.___\_<|>_/___.' _> \"".
    //          | | :  `- \`. ;`. _/; .'/ /  .' ; |
    //          \  \ `-.   \_\_`. _.'_/_/  -' _.' /
    //===========`-.`___`-.__\ \___  /__.-'_.'_.-'================
    //                        `=--=-'                    hjw
    //魚 心 あ れ ば 水 心

    //Методы:
    //1. Обновление пароля - POST - DTO: New Password
    //2. Получение информации об авторизованном пользователе - GET - DTO: User
    //3. Обновление информации об авторизованном пользователе - PATCH - DTO: UpdateUser
    //4. Обновление аватара авторизованного пользователя - PATCH

    private final UserRepository userRepository;
    private final UsersMapping usersMapping;

    public UsersServiceImpl(UserRepository userRepository, UsersMapping userMapping) {
        this.userRepository = userRepository;
        this.usersMapping = userMapping;
    }

    @Override
    public boolean changePassword(NewPassword newPassword, String username) {
        if ((newPassword.getCurrentPassword()).equals(newPassword.getNewPassword())) {
            UserEntity authorizedUser = userRepository.findByUsername(username);
            authorizedUser.setPassword(newPassword.getNewPassword());
            userRepository.save(authorizedUser);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User retrieveAuthorizedUserInformation(String username) {
            UserEntity authorizedUser = userRepository.findByUsername(username);
            User authorizedUserMapped = usersMapping.userEntityToUserDto(authorizedUser);
            return authorizedUserMapped;

    }

    @Override
    public UpdateUser patchAuthorizedUserInformation(UpdateUser updateUser, String username) {
            UserEntity authorizedUser = userRepository.findByUsername(username);
            authorizedUser.setFirstName(updateUser.getFirstName());
            authorizedUser.setLastName(updateUser.getLastName());
            authorizedUser.setPhone(updateUser.getPhone());
            userRepository.save(authorizedUser);
            return updateUser;
    }


}
