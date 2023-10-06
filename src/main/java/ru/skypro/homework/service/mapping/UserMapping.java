package ru.skypro.homework.service.mapping;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.model.UserEntity;

//@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapping {

    public UserEntity newPasswordDtoToUser (NewPassword newPassword);

    public ru.skypro.homework.dto.User userEntityToUserDto (UserEntity userEntityInput);

    public UserEntity updateUserDtoToUserEntity (UpdateUser updateUser) ;
}
