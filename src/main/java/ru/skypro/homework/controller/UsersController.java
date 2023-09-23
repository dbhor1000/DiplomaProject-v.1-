package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.CurrentUser;
import ru.skypro.homework.dto.Login;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.SetPassw;
import ru.skypro.homework.service.AuthService;
import ru.skypro.homework.service.UsersService;

import java.security.Principal;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
public class UsersController {

    private final UsersService usersService;

    @PostMapping("/users/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword newPassword, Principal principal) {
        if (usersService.setPassword(newPassword.getCurrentPassword(), newPassword.getNewPassword())) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/users/me")
    public ResponseEntity<?> setPassword() {
        if (usersService.getUserInfo()) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    //@PostMapping("/register")
    //public ResponseEntity<?> register(@RequestBody Register register) {
    //    if (authService.register(register)) {
    //        return ResponseEntity.status(HttpStatus.CREATED).build();
    //    } else {
    //        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    //    }
    //}

}
