package ru.skypro.homework.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN, reason = "Доступ к комментарию невозможен. Проверьте правильность запроса.")
public class NoAccessToCommentException extends RuntimeException {

    public NoAccessToCommentException() { super("Доступ к комментарию запрещён!"); }
}
