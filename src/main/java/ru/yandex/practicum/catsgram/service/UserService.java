package ru.yandex.practicum.catsgram.service;

import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.model.User;
import ru.yandex.practicum.catsgram.controller.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.controller.exception.UserAlreadyExistException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private Map<String, User> userMap = new HashMap<>();

    public List<User> getUserList() {
        return new ArrayList<>(userMap.values());
    }

    public User addInUserList(User user) throws UserAlreadyExistException, InvalidEmailException {
//        try {
            if (userMap.containsValue(user)) {
                throw new UserAlreadyExistException("Такой пользователь уже существует");
            }
            checkEmail(user);
                this.userMap.put(user.getEmail(), user);

//        } catch (UserAlreadyExistException | InvalidEmailException exception) {
//            System.out.println(exception.getMessage());
//        }
        return userMap.get(user.getEmail());
    }

    public User updateOrAddInUserList(User user) throws InvalidEmailException {
//        try {
            checkEmail(user);
            if (userMap.containsValue(user)) {
                this.userMap.replace(user.getEmail(), user);
            } else {
                this.userMap.put(user.getEmail(), user);
            }
//        } catch (InvalidEmailException e) {
//            System.out.println(e.getMessage());
//        }
        return userMap.get(user.getEmail());
    }

    public User findUserByEmail(String email) {
        if (email == null) {
            return null;
        }
        return userMap.getOrDefault(email, null);
    }

    public User getUserByEmail(String email) {
        if (email == null) {
            return null;
        }
        return userMap.get(email);
    }

    private void checkEmail(User user) throws InvalidEmailException {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new InvalidEmailException("Адрес электронной почты не может быть пустым.");
        }
    }
}
