package ru.skypro.homework.service.mapping;

import ru.skypro.homework.dto.ExtendedLoginViaDB;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.model.UserEntity;

public interface UsersMapping {

    public UserEntity newPasswordDtoToUser (NewPassword newPassword);

    public ru.skypro.homework.dto.User userEntityToUserDto (UserEntity userEntityInput);

    public UserEntity updateUserDtoToUserEntity (UpdateUser updateUser);

    public ExtendedLoginViaDB extendedLoginViaDB (UserEntity userEntity);

    public UserEntity registerDTOtoUserEntity (Register register);
}