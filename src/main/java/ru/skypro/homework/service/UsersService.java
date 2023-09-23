package ru.skypro.homework.service;

import ru.skypro.homework.dto.ChangeInfoAuthorized;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.User;


public interface UsersService {

    boolean setPassword(String oldPassword, String newPassword);

    User getUserInfo();

    //boolean patchAuthorized(ChangeInfoAuthorized changeInfoAuthorized);
}
