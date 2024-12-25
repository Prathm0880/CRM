package com.crm.controller;

import com.crm.entity.Post;
import com.crm.repository.PostRepository;
import org.hibernate.engine.transaction.jta.platform.internal.SynchronizationRegistryBasedSynchronizationStrategy;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/post")
public class PostController {
    private PostRepository postRepository;

    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @PostMapping
    public String createPost(@RequestBody Post post) {
        postRepository.save(post);
        return "Post created Successfully.";
    }
    @DeleteMapping
    public String deletePost(@RequestParam long id){
        postRepository.deleteById(id);
        return "Post delete Successfully";
    }

}
