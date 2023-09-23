package ru.skypro.homework.dto;

import lombok.Data;
import org.springdoc.api.ErrorMessage;

import javax.validation.constraints.Size;

@Data
public class NewPassword {

    @Size(min = 8, max = 16, message = "Текущий пароль слишком длинный или короткий.")
    private String currentPassword;
    @Size(min = 8, max = 16, message = "Новый пароль слишком длинный или короткий.")
    private String newPassword;

}
