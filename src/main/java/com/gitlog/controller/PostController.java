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

    //게시글 가져오기
    @GetMapping("/api/posts")
    public Page<Post> getComment(@RequestParam("page") int page,
                                 @RequestParam("size") int size){
        page = page -1;
        return postService.getPost(page,size);

    }

    //게시글 작성
    @PostMapping("/api/posts")
    public void writeComment(@RequestBody PostRequestDto postRequestDto){
        postService.createPost(postRequestDto);
    }

    //게시글 수정
    @PutMapping("/api/posts/{post_id}")
    public void updatePost(@PathVariable Long post_id, @RequestBody PostRequestDto postRequestDto){
        postService.update(post_id, postRequestDto);
    }

    @DeleteMapping("/api/posts/{post_id}")
    public void deletePost(@PathVariable Long post_id){
        postService.deletePost(post_id);
    }

}
