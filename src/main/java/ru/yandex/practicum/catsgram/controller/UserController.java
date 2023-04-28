package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.controller.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.controller.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUserList() {
        return userService.getUserList();
    }

    @PostMapping
    public User addInUserList(@RequestBody User user) throws UserAlreadyExistException, InvalidEmailException {
        return userService.addInUserList(user);
    }

    @PutMapping
    public User updateOrAddInUserList(@RequestBody User user) throws InvalidEmailException {
        return userService.updateOrAddInUserList(user);
    }

    @GetMapping("/{email}")
    public User getUserByEmail(@PathVariable String email) {
        System.out.println("Получили email: " + email);
        return userService.getUserByEmail(email);
    }
}
