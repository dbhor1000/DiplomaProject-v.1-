package ru.skypro.homework.service.mapping;


import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.UserEntity;

import java.util.Arrays;

@Component
public class UsersMappingImpl implements UsersMapping {

    //Необходимые мапперы:
    //NewPassword(DTO) -> User(Entity)
    //User(Entity) -> User(DTO)
    //UpdateUser(DTO) -> User(Entity)

    @Override
    public UserEntity newPasswordDtoToUser (NewPassword newPassword) {
        if (newPassword == null) {
            return null;
        }

        UserEntity user = new UserEntity();
        user.setPassword(newPassword.getNewPassword());
        return user;
    }

    @Override
    public ru.skypro.homework.dto.User userEntityToUserDto (UserEntity userEntityInput) {
        if (userEntityInput == null) {
            return null;
        }

        ru.skypro.homework.dto.User user = new ru.skypro.homework.dto.User();
        user.setId(userEntityInput.getId());
        user.setEmail(userEntityInput.getUsername());
        user.setFirstName(userEntityInput.getFirstName());
        user.setLastName(userEntityInput.getLastName());
        user.setPhone(userEntityInput.getPhone());
        user.setRole(userEntityInput.getRole());
        user.setImage(Arrays.toString(userEntityInput.getImage()));
        return user;
    }

    @Override
    public UserEntity updateUserDtoToUserEntity (UpdateUser updateUser) {
        if (updateUser == null) {
            return null;
        }

        UserEntity user = new UserEntity();
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setPhone(user.getPhone());
        return user;
    }

    @Override
    public ExtendedLoginViaDB extendedLoginViaDB (UserEntity userEntity){
        if (userEntity == null) {
            return null;
        }

        ExtendedLoginViaDB extendedLoginViaDB = new ExtendedLoginViaDB();
        extendedLoginViaDB.setPassword(userEntity.getPassword());
        extendedLoginViaDB.setUsername(userEntity.getUsername());
        extendedLoginViaDB.setRole(userEntity.getRole());
        return extendedLoginViaDB;
    }

    @Override
    public UserEntity registerDTOtoUserEntity (Register register){
        if (register == null) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(register.getUsername());
        userEntity.setFirstName(register.getFirstName());
        userEntity.setLastName(register.getLastName());
        userEntity.setPassword(register.getPassword());
        userEntity.setPhone(register.getPhone());
        userEntity.setRole(register.getRole());
        return userEntity;
    }
}

