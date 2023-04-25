package ru.yandex.practicum.catsgram.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

        if (friendsParameters != null) {
            List<Post> result = new ArrayList<>();
            for (String friend : friendsParameters.friends) {
                result.addAll(postService.findAllByUserEmail(friend, friendsParameters.size, friendsParameters.sort));
            }
            return result;
        } else {
            throw new RuntimeException("неверно заполнены параметры");
        }
    }

    static class FriendsParameters {
        private String sort;
        private Integer size;
        private List<String> friends;

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public Integer getSize() {
            return size;
        }

        public void setSize(Integer size) {
            this.size = size;
        }

        public List<String> getFriends() {
            return friends;
        }

        public void setFriends(List<String> friends) {
            this.friends = friends;
        }
    }
}
