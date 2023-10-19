package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;
import ru.skypro.homework.model.Image;
import ru.skypro.homework.model.UserEntity;
import ru.skypro.homework.repository.ImageRepository;
import ru.skypro.homework.repository.UserRepository;
import ru.skypro.homework.service.UsersService;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class UsersController {

    //Методы:
    //1. Обновление пароля - POST - DTO: New Password
    //2. Получение информации об авторизованном пользователе - GET - DTO: User
    //3. Обновление информации об авторизованном пользователе - PATCH - DTO: UpdateUser
    //4. Обновление аватара авторизованного пользователя - PATCH

    private final UsersService usersService;
    private final UserRepository userRepository; // **
    private final ImageRepository imageRepository; //**

    //public UsersController(UsersService usersService) {
    //    this.usersService = usersService;
    //}

    public UsersController(UsersService usersService, UserRepository userRepository, ImageRepository imageRepository) {
        this.usersService = usersService;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
    }


    //
    //Метод работает.

    @PostMapping("/set_password")
    public ResponseEntity<?> changePassword(@RequestBody NewPassword newPassword, Authentication authentication) {

        if (usersService.changePassword(newPassword, authentication.getName())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    //
    //Метод работает.

    @GetMapping("/me")
    public ResponseEntity<?> getAuthorizedUserInfo(Authentication authentication) {

        User userRetrieved = usersService.retrieveAuthorizedUserInformation(authentication.getName());
        return ResponseEntity.ok(userRetrieved);

    }

    //
    //Метод работает.
    @PatchMapping("/me")
    public ResponseEntity<?> patchAuthorizedUser(@RequestBody UpdateUser updateUser, Authentication authentication) {

        UpdateUser updatedUser = usersService.patchAuthorizedUserInformation(updateUser, authentication.getName());
        return ResponseEntity.ok(updatedUser);
    }

    //

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editAuthorizedUserProfilePic(@RequestParam("image") MultipartFile linkedPicture, Authentication authentication) {

        usersService.patchAuthorizedUserPicture(linkedPicture, authentication.getName());
        return ResponseEntity.ok().build();
    }

    @Transactional
    @GetMapping(value = "/{id}/avatar", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, "image/*"})
    public byte[] getImage(@PathVariable Integer id) throws IOException {

        //Метод получает картинку в формате byte[] из Entity Image, хранящегося в репозитории.
        //Изображения в Ad и UserEntity хранятся в качестве ссылок на объекты в репозитории Image.

        Image image = imageRepository.getReferenceById(id);
        return image.getImage();
    }
}