package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.catsgram.controller.exception.IncorrectParameterException;
import ru.yandex.practicum.catsgram.model.FriendsParameters;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PostFeedController {

    private final PostService postService;

    @Autowired
    public PostFeedController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/feed/friends")
    List<Post> getFriendsFeed(@RequestBody String parameters) {
        ObjectMapper objectMapper = new ObjectMapper();
        FriendsParameters friendsParameters;

        try {
            String paramsFromString = objectMapper.readValue(parameters, String.class);
            friendsParameters = objectMapper.readValue(paramsFromString, FriendsParameters.class);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("Невалидный формат json", exception);
        }
        if (friendsParameters.getSize() == null || friendsParameters.getSize() <= 0) {
            throw new IncorrectParameterException("size");
        }

        if (friendsParameters.getSort() == null || !friendsParameters.getSort().equals("asc") || !friendsParameters.getSort().equals("desc")) {
            throw new IncorrectParameterException("sort");
        }

        if (friendsParameters.getFriends() == null || friendsParameters.getFriends().isEmpty()) {
            throw new IncorrectParameterException("friends");
        }

        List<Post> result = new ArrayList<>();
        for (String friend : friendsParameters.getFriends()) {
            result.addAll(postService.findAllByUserEmail(friend, friendsParameters.getSize(),
                    friendsParameters.getSort()));
        }
        return result;
    }
}
