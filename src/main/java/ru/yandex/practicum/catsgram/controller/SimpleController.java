package ru.yandex.practicum.catsgram.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.service.HackCatService;

import java.util.Optional;

@RestController
public class SimpleController {

    private final HackCatService hackCatService;

    public SimpleController(HackCatService hackCatService) {
        this.hackCatService = hackCatService;
    }

    // внедрите бин HackCatService

    @GetMapping("/do-hack")
    public Optional<String> doHack() {
        // хакните этих котиков
        return hackCatService.doHackNow()
                .map(password -> "Пароль: " + password + " - верный")
                .or(() -> Optional.of("Не удалось подобрать правильный пароль"));
    }

    @GetMapping("/home")
    public String homePage() {
        return "Котограм";
    }
}