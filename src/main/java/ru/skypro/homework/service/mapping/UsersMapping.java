package ru.skypro.homework.service.mapping;

import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.model.UserEntity;

//@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsersMapping {

    public UserEntity newPasswordDtoToUser (NewPassword newPassword);

    public ru.skypro.homework.dto.User userEntityToUserDto (UserEntity userEntityInput);

    public UserEntity updateUserDtoToUserEntity (UpdateUser updateUser);
}