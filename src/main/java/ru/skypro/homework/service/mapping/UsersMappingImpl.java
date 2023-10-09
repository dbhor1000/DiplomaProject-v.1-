package ru.skypro.homework.service.mapping;


import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.model.UserEntity;

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
        user.setEmail(userEntityInput.getEmail());
        user.setFirstName(userEntityInput.getFirstName());
        user.setLastName(userEntityInput.getLastName());
        user.setPhone(userEntityInput.getPhone());
        user.setRole(userEntityInput.getRole());
        user.setImage(userEntityInput.getImage());
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
}

