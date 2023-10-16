package ru.skypro.homework.service.impl;

import org.apache.commons.io.FilenameUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.exception.UserNotFoundException;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UsersService;
import ru.skypro.homework.service.mapping.UsersMapping;

import javax.persistence.Query;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;


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
    private final PasswordEncoder bCryptPasswordEncoder;

    public UsersServiceImpl(UserRepository userRepository, UsersMapping usersMapping, PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.usersMapping = usersMapping;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public boolean changePassword(NewPassword newPassword, String username) {

        UserEntity authorizedUser = userRepository.findByUsername(username);
        if (bCryptPasswordEncoder.matches(newPassword.getCurrentPassword(), authorizedUser.getPassword())) {
            authorizedUser.setPassword(bCryptPasswordEncoder.encode(newPassword.getNewPassword()));
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

    @Override
    public boolean patchAuthorizedUserPicture(MultipartFile image, String username){

        UserEntity activeUser = userRepository.findByUsername(username);

        try {

            String extension = FilenameUtils.getExtension(image.getOriginalFilename());
            byte[] imageToBytes = image.getBytes();
            Path write = Files.write(Paths.get(UUID.randomUUID() + "." + extension), imageToBytes);
            activeUser.setImage(write.toString());//Сохраняем изображение как строку, получившуюся из массива байтов при конвертации. Далее, можно конвертировать обратно.

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        userRepository.save(activeUser);
        return true;

    }

}
