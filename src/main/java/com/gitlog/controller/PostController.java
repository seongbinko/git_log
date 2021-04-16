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
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;
    private final PostRepository postRepository;

    //게시글 가져오기
    @GetMapping("/api/posts")
    public Page<PostResponseDto> getPosts(@RequestParam("page") int page,
                                          @RequestParam("size") int size,
                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.getPost(page, size, userDetails);
    }

    //게시글 작성
    @PostMapping("/api/posts")
    public ResponseEntity<String> writePost(@ModelAttribute PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return postService.createPost(postRequestDto, userDetails.getAccount());
    }

    //게시글 수정
    @PutMapping("/api/posts/{post_id}")
    public ResponseEntity<String> updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long post_id, @ModelAttribute PostRequestDto postRequestDto) throws IOException {
        return postService.updatePost(post_id, postRequestDto, userDetails);
    }
    //게시글 삭제
    @DeleteMapping("/api/posts/{post_id}")
    public ResponseEntity<String> deletePost(@PathVariable Long post_id) {
        return postService.deletePost(post_id);
    }
}
