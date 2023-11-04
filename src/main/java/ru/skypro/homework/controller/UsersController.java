package ru.skypro.homework.controller;

import lombok.extern.slf4j.Slf4j;
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
import ru.skypro.homework.service.ImageService;
import ru.skypro.homework.service.UsersService;

import javax.transaction.Transactional;
import java.io.IOException;

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
    private final ImageService imageService;

    public UsersController(UsersService usersService, ImageService imageService) {
        this.usersService = usersService;
        this.imageService = imageService;
    }

    /**
     * Контроллер для смены пароля авторизованного пользователя.
     * @param authentication, DTO объект со старым и новым паролем
     * @return сообщение об успехе или ошибке
     */

    @PostMapping("/set_password")
    public ResponseEntity<?> changePassword(@RequestBody NewPassword newPassword, Authentication authentication) {

        if (usersService.changePassword(newPassword, authentication.getName())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * Контроллер для поулчения данных авторизованного пользователя.
     * @param authentication
     * @return DTO объект User с данными пользователя
     */

    @GetMapping("/me")
    public ResponseEntity<?> getAuthorizedUserInfo(Authentication authentication) {

        User userRetrieved = usersService.retrieveAuthorizedUserInformation(authentication.getName());
        return ResponseEntity.ok(userRetrieved);

    }

    /**
     * Контроллер для редактирования данных авторизованного пользователя.
     * @param authentication, DTO объект с новыми данными пользователя
     * @return DTO объект с новыми данными пользователя
     */
    @PatchMapping("/me")
    public ResponseEntity<?> patchAuthorizedUser(@RequestBody UpdateUser updateUser, Authentication authentication) {

        UpdateUser updatedUser = usersService.patchAuthorizedUserInformation(updateUser, authentication.getName());
        return ResponseEntity.ok(updatedUser);
    }

    /**
     * Контроллер для редактирования изображения авторизорванного пользователя.
     * @param authentication, id объявления, MultipartFile новго изображения
     * @return сообщение об успехе
     */

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editAuthorizedUserProfilePic(@RequestParam("image") MultipartFile linkedPicture, Authentication authentication) {

        usersService.patchAuthorizedUserPicture(linkedPicture, authentication.getName());
        return ResponseEntity.ok().build();
    }

    /**
     * Контроллер для получения изображения из базы данных. Служебный контроллер, необходимый для правильного функционирования front-end части.
     * @param id запрашиваемого из базы изображения
     * @return изображение в формате byte[]
     */

    @Transactional
    @GetMapping(value = "/{id}/avatar", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_GIF_VALUE, "image/*"})
    public byte[] getImage(@PathVariable Integer id) throws IOException {

        //Метод получает картинку в формате byte[] из Entity Image, хранящегося в репозитории.
        //Изображения в Ad и UserEntity хранятся в качестве ссылок на объекты в репозитории Image.

        Image image = imageService.callImageById(id);
        return image.getImage();
    }
}