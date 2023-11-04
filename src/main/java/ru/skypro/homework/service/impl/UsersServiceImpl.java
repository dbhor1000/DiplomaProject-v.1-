package ru.skypro.homework.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UsersService;
import ru.skypro.homework.service.mapping.UsersMapping;

import javax.transaction.Transactional;
import java.io.IOException;


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
    private final ImageRepository imageRepository;

    public UsersServiceImpl(UserRepository userRepository, UsersMapping usersMapping, PasswordEncoder bCryptPasswordEncoder, ImageRepository imageRepository) {
        this.userRepository = userRepository;
        this.usersMapping = usersMapping;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.imageRepository = imageRepository;
    }

    /**
     * Сервисный метод для смены пароля авторизованного пользователя.
     * @param newPassword DTO-object NewPassword и username авторизованного пользователя
     * @return boolean true/false
     */


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

    /**
     * Сервисный метод для получения данных авторизованного пользователя.
     * @param username авторизованного пользователя
     * @return DTO-object User
     */


    @Override
    public User retrieveAuthorizedUserInformation(String username) {
            UserEntity authorizedUser = userRepository.findByUsername(username);
            User authorizedUserMapped = usersMapping.userEntityToUserDto(authorizedUser);
            return authorizedUserMapped;

    }

    /**
     * Сервисный метод для редактирования данных авторизованного пользователя.
     * @param updateUser DTO-object UpdateUser, username авторизованного пользователя
     * @return DTO-object UpdateUser
     */


    @Override
    public UpdateUser patchAuthorizedUserInformation(UpdateUser updateUser, String username) {
            UserEntity authorizedUser = userRepository.findByUsername(username);
            authorizedUser.setFirstName(updateUser.getFirstName());
            authorizedUser.setLastName(updateUser.getLastName());
            authorizedUser.setPhone(updateUser.getPhone());
            userRepository.save(authorizedUser);
            return updateUser;
    }

    /**
     * Сервисный метод для редактирования изображения авторизованного пользователя.
     * @param image MultipartFile изображения, username авторизованного пользователя
     * @return boolean true/false
     */


    @Transactional
    @Override
    public boolean patchAuthorizedUserPicture(MultipartFile image, String username){
        UserEntity activeUser = userRepository.findByUsername(username);

        try {
            byte[] imageToBytes = image.getBytes();
            Image multipartToEntity = new Image();
            multipartToEntity.setImage(imageToBytes);
            imageRepository.save(multipartToEntity);
            activeUser.setImageAvatar(multipartToEntity);//Сохраняем изображение как строку, получившуюся из массива байтов при конвертации. Далее, можно конвертировать обратно.

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        userRepository.save(activeUser);
        return true;
    }
}
