package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.User;

public interface UsersService {

    public boolean changePassword (NewPassword newPassword, String username);
    public User retrieveAuthorizedUserInformation(String username);
    public UpdateUser patchAuthorizedUserInformation(UpdateUser updateUser, String username);
    boolean patchAuthorizedUserPicture(MultipartFile image, String username);

}
