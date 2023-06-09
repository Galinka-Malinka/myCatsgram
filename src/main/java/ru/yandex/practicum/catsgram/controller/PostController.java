package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.controller.exception.IncorrectParameterException;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;

import java.util.List;

@RestController
public class PostController {
    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/posts")
    public List<Post> findAll(@RequestParam(value = "size", defaultValue = "10", required = false) Integer size,
                              @RequestParam(value = "sort", defaultValue = "desc", required = false) String sort,
                              @RequestParam(value = "page", defaultValue = "0", required = false) Integer page) {

        if (page < 0) {
            throw new IncorrectParameterException("page");
        }

        if (size <= 0) {
            throw new IncorrectParameterException("size");
        }

        if (!(sort.equals("asc") || sort.equals("desc"))) {
            throw new IncorrectParameterException("sort");
        }

        Integer from = page * size;
        return postService.findAll(size, sort, from);
    }

    @PostMapping(value = "/post")
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

    @GetMapping("/post/{id}")
    public Post findPostById(@PathVariable int id) {
        return postService.findPostById(id);
    }
}