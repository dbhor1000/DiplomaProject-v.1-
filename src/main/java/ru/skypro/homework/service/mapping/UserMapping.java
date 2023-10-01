package ru.skypro.homework.service.mapping;

import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.model.User;

@Mapper
public interface UserMapping {

    public User newPasswordDtoToUser (NewPassword newPassword);

    public ru.skypro.homework.dto.User userEntityToUserDto (User userEntityInput);

    public User updateUserDtoToUserEntity (UpdateUser updateUser) ;
}
