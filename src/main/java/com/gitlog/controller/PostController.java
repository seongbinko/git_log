package com.gitlog.controller;

import com.gitlog.dto.PostRequestDto;
import com.gitlog.model.Comment;
import com.gitlog.model.Post;
import com.gitlog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;

    @GetMapping("/api/posts")
    public Page<Post> getComment(@RequestParam("page") int page,
                                 @RequestParam("size") int size){
        page = page -1;
        return postService.getPost(page,size);

    }
    @PostMapping("/api/post")
    public void writeComment(@RequestBody PostRequestDto postRequestDto){
        postService.createPost(postRequestDto);
    }
    @PutMapping("/api/post/{post_id}")
    public void updatePost(@PathVariable Long post_id, @RequestBody PostRequestDto postRequestDto){
        postService.update(post_id, postRequestDto);
    }

}
