package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Доступ к пользователю невозможен. Проверьте правильность запроса.")
public class NoAccessToUserException extends RuntimeException {

    public NoAccessToUserException() { super("Доступ к пользователю запрещён!"); }
}