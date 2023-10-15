package ru.skypro.homework.dto;

import lombok.Data;
import org.springdoc.api.ErrorMessage;

import javax.validation.constraints.Size;

public class NewPassword {

    @Size(min = 8, max = 16, message = "Текущий пароль слишком длинный или короткий.")
    private String currentPassword;
    @Size(min = 8, max = 16, message = "Новый пароль слишком длинный или короткий.")
    private String newPasswordForUpdate;

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPasswordForUpdate() {
        return newPasswordForUpdate;
    }

    public void setNewPasswordForUpdate(String newPasswordForUpdate) {
        this.newPasswordForUpdate = newPasswordForUpdate;
    }
}
