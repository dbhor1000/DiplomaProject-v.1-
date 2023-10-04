package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.Size;


@Data
public class Login {

    @Size(min = 8, max = 16, message = "Проверьте количество символов.")
    private String username;
    @Size(min = 4, max = 32, message = "Проверьте количество символов.")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
