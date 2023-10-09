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
import ru.skypro.homework.service.UsersService;

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

    public UsersController(UsersService usersService) {
        this.usersService = usersService;
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

    @GetMapping("/me")
    public ResponseEntity<?> getAuthorizedUserInfo(Authentication authentication) {

        User userRetrieved = usersService.retrieveAuthorizedUserInformation(authentication.getName());
        return ResponseEntity.ok(userRetrieved);

    }

    //

    @PatchMapping("/me")
    public ResponseEntity<?> patchAuthorizedUser(@RequestBody UpdateUser updateUser, Authentication authentication) {

        UpdateUser updatedUser = usersService.patchAuthorizedUserInformation(updateUser, authentication.getName());
        return ResponseEntity.ok(updatedUser);
    }

    //

    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> editAuthorizedUserProfilePic(@RequestParam("image") MultipartFile linkedPicture) {
        return ResponseEntity.ok().build();
    }
}