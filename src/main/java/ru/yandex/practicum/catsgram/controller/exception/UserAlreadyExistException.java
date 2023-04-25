package ru.yandex.practicum.catsgram.controller.exception;

public class UserAlreadyExistException extends Throwable {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}
