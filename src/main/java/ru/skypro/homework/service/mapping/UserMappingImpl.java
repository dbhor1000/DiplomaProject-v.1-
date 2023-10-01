package ru.skypro.homework.service.mapping;

import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;

@Component
public class UserMappingImpl implements UserMapping {

    //Необходимые мапперы:
    //NewPassword(DTO) -> User(Entity)
    //User(Entity) -> User(DTO)
    //UpdateUser(DTO) -> User(Entity)

    @Override
    public User newPasswordDtoToUser (NewPassword newPassword) {
        if (newPassword == null) {
            return null;
        }

        User user = new User();
        user.setPassword(newPassword.getNewPassword());
        return user;
    }

    @Override
    public ru.skypro.homework.dto.User userEntityToUserDto (User userEntityInput) {
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
    public User updateUserDtoToUserEntity (UpdateUser updateUser) {
        if (updateUser == null) {
            return null;
        }

        User user = new User();
        user.setFirstName(updateUser.getFirstName());
        user.setLastName(updateUser.getLastName());
        user.setPhone(user.getPhone());
        return user;
    }
}
