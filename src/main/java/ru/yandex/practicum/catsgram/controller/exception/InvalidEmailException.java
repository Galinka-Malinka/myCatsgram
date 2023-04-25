package ru.yandex.practicum.catsgram.controller.exception;

public class InvalidEmailException extends Throwable {
    public InvalidEmailException(String message) {
        super(message);
    }
}
