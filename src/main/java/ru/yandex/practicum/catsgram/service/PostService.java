package ru.yandex.practicum.catsgram.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.catsgram.controller.exception.PostNotFoundException;
import ru.yandex.practicum.catsgram.controller.exception.UserNotFoundException;
import ru.yandex.practicum.catsgram.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {
    private final List<Post> posts = new ArrayList<>();
    private final UserService userService;

    private Integer id = 0;

    @Autowired
    public PostService(UserService userService) {
        this.userService = userService;
    }

    public List<Post> findAll(Integer size, String sort, Integer from) {
        return posts.stream().sorted((p0, p1)-> {
            int compare = p0.getCreationDate().compareTo(p1.getCreationDate());
            if (sort.equals("desc")) {
                compare = -1 * compare;
            }
            return compare;
        }).skip(from).limit(size).collect(Collectors.toList());
    }

    public Post create(Post post) {
        try {
            if (userService.findUserByEmail(post.getAuthor()) != null) {
                post.setId(id + 1);
                id++;
                posts.add(post);
            } else {
                throw new UserNotFoundException(String.format(
                        "Пользователь %s не найден",
                        post.getAuthor()));
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return post;
    }

    public Post findPostById(int id) {
        return posts.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new PostNotFoundException(String.format("Пост № %d не найден", id)));
    }

    public List<Post> findAllByUserEmail(String friend, Integer size, String sort) {
        List<Post> postList = new ArrayList<>();
//        try {
//            if (userService.findUserByEmail(friend) != null) {
                postList.stream().filter(p -> p.getAuthor().equals(friend))
                        .sorted((p0, p1)-> {
                    int compare = p0.getCreationDate().compareTo(p1.getCreationDate());
                    if (sort.equals("desc")) {
                        compare = -1 * compare;
                    }
                    return compare;
                }).limit(size).collect(Collectors.toList());
//            } else {
//                throw new UserNotFoundException(String.format(
//                        "Пользователь %s не найден",
//                        friend));
//            }
//        } catch (UserNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
        return  postList;



    }
}