package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Login;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UserService;

import java.security.Principal;


@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class UserController {

    //private final UserService usersService;

    //@PostMapping("/users/set_password")
    //public ResponseEntity<?> setPassword(@RequestBody NewPassword newPassword, Principal principal) {
    //    if (usersService.setPassword(newPassword.getCurrentPassword(), newPassword.getNewPassword())) {
    //        return ResponseEntity.ok().build();
    //    } else {
    //        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    //    }

    //@GetMapping("/users/me")
    //public ResponseEntity<?> setPassword () {
    //    if (usersService.getUserInfo()) {
    //        return ResponseEntity.ok().build();
    //    } else {
    //        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    //    }
    //}

    //@PostMapping("/register")
    //public ResponseEntity<?> register(@RequestBody Register register) {
    //    if (authService.register(register)) {
    //        return ResponseEntity.status(HttpStatus.CREATED).build();
    //    } else {
    //        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    //    }
    //}

    //

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "запрос выполнен;"
            ),
            @ApiResponse(
                    responseCode = "401", description = "отсутствует авторизация;"
            ),
            @ApiResponse(
                    responseCode = "403", description = "операция запрещена;"
            )
    }
    )
    @PostMapping("/users/set_password")
    public ResponseEntity<?> changePassword(@RequestBody NewPassword newPassword) {
        return ResponseEntity.ok().build();
    }

    //

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "запрос выполнен;"
            ),
            @ApiResponse(
                    responseCode = "401", description = "отсутствует авторизация;"
            )
    }
    )
    @GetMapping("/users/me")
    public ResponseEntity<?> getAuthorizedUserInfo() {
        return ResponseEntity.ok().build();
    }

    //

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "запрос выполнен;"
            ),
            @ApiResponse(
                    responseCode = "401", description = "отсутствует авторизация;"
            )
    }
    )
    @PatchMapping("/users/me")
    public ResponseEntity<?> patchAuthorizedUser(@RequestBody UpdateUser updateUser) {
        return ResponseEntity.ok().build();
    }

    //

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200", description = "запрос выполнен;"
            ),
            @ApiResponse(
                    responseCode = "401", description = "отсутствует авторизация;"
            )
    }
    )
    @PatchMapping("/users/me/image")
    public ResponseEntity<?> editAuthorizedUserProfilePic(@RequestBody String linkedPicture) {
        return ResponseEntity.ok().build();
    }
}