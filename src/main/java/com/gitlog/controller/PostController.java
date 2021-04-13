package com.gitlog.controller;

import com.gitlog.config.UserDetailsImpl;
import com.gitlog.dto.PostRequestDto;
import com.gitlog.dto.PostResponseDto;
import com.gitlog.model.Post;
import com.gitlog.repository.PostRepository;
import com.gitlog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    //게시글 가져오기
    @GetMapping("/api/posts")
    public Page<PostResponseDto> getPosts(@RequestParam("page") int page,
                                          @RequestParam("size") int size) {
        return postService.getPost(page, size);
    }

    //게시글 작성
    @PostMapping("/api/posts")
    public ResponseEntity writePost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.createPost(postRequestDto, userDetails.getAccount());
        return ResponseEntity.ok().build();
    }

    //게시글 수정
    @PutMapping("/api/posts/{post_id}")
    public void updatePost(@PathVariable Long post_id, @RequestBody PostRequestDto postRequestDto) {
        postService.updatePost(post_id, postRequestDto);
    }

    @DeleteMapping("/api/posts/{post_id}")
    public void deletePost(@PathVariable Long post_id) {
        postService.deletePost(post_id);
    }

}
