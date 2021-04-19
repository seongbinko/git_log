package com.gitlog.controller;

import com.gitlog.config.UserDetailsImpl;
import com.gitlog.dto.*;
import com.gitlog.model.Account;
import com.gitlog.model.Post;
import com.gitlog.repository.AccountRepository;
import com.gitlog.repository.PostRepository;
import com.gitlog.service.FileUploadService;
import com.gitlog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final PostService postService;
    private final AccountRepository accountRepository;

    private final FileUploadService fileUploadService;

    // Todo 성능 개선
    @GetMapping("/story/{nickname}")
    public ResponseEntity readPostsByNickname(
            @PathVariable String nickname
    ) {
        Account account = accountRepository.findByNickname(nickname).orElse(null);
        if(account == null) {
            return ResponseEntity.badRequest().build();
        }
        AccountResponseDto accountResponseDto = new AccountResponseDto(
                account.getNickname(),
                account.getBio(),
                account.getProfileImgUrl(),
                account.getGithubUrl()
        );
        List<Post> posts = postRepository.findByAccountOrderByCreatedAtDesc(account);
        List<PostResponseDto> toList = posts.stream().map(
                post -> new PostResponseDto(
                        post.getId(),
                        post.getContent(),
                        post.getImgUrl(),
                        post.getCreatedAt(),
                        post.getCreatedBy(),
                        post.getModifiedAt(),
                        post.getComments().stream().map(
                                comment -> new CommentResponseDto(
                                        comment.getId(),
                                        comment.getContent(),
                                        comment.getCreatedAt(),
                                        comment.getCreatedBy(),
                                        new AccountResponseDto(comment.getAccount().getProfileImgUrl())
                                )
                        ).collect(Collectors.toList()),
                        new AccountResponseDto(post.getAccount().getProfileImgUrl()),
                        post.getComments().size(),
                        post.getHearts().size()
                )).collect(Collectors.toList());

        Map<String, Object> map = new HashMap<>();
        map.put("account", accountResponseDto);
        map.put("posts", toList);
        return ResponseEntity.ok().body(map);
    }

    // Todo 성능 개선
    @GetMapping("/api/posts")
    public Page<PostResponseDto> readPosts(
            @RequestParam("page") int page,
            @RequestParam("size") int size
    ) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> posts = postRepository.findAll(pageRequest);
        Page<PostResponseDto> toMap = posts.map(post -> new PostResponseDto(
                post.getId(),
                post.getContent(),
                post.getImgUrl(),
                post.getCreatedAt(),
                post.getCreatedBy(),
                post.getModifiedAt(),
                post.getComments().stream().map(
                    comment -> new CommentResponseDto(
                            comment.getId(),
                            comment.getContent(),
                            comment.getCreatedAt(),
                            comment.getCreatedBy(),
                            new AccountResponseDto(comment.getAccount().getProfileImgUrl())
                    )
                ).collect(Collectors.toList()),
                new AccountResponseDto(post.getAccount().getProfileImgUrl()),
                post.getComments().size(),
                post.getHearts().size()
        ));
        return toMap;
    }

    @PostMapping("/api/posts")
    public ResponseEntity savePost(@AuthenticationPrincipal UserDetailsImpl userDetails, @Valid @ModelAttribute PostRequestDto postRequestDto, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        Account account = accountRepository.findByNickname(userDetails.getUsername()).orElse(null);
        String imgUrl = fileUploadService.uploadImage(postRequestDto.getImg());
        postService.savePost(account, postRequestDto, imgUrl);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/api/posts/{post_id}")
    public ResponseEntity updatePost(@AuthenticationPrincipal UserDetailsImpl userDetails
            , @PathVariable Long post_id
            , @Valid @ModelAttribute PostUpdateRequestDto postUpdateRequestDto, Errors errors) {
        if(errors.hasErrors()) {
            return ResponseEntity.badRequest().body(errors.getAllErrors());
        }
        Post post = postRepository.findById(post_id).orElse(null);
        if(post == null || !userDetails.getUsername().equals(post.getCreatedBy())) {
            return ResponseEntity.badRequest().build(); //Todo 메시지를 전송하는 방식으로
        }

        String newImgUrl = null;
        if(postUpdateRequestDto.getImg() != null) {
            fileUploadService.removeImage(post.getImgUrl());
            newImgUrl = fileUploadService.uploadImage(postUpdateRequestDto.getImg());
        }
        postService.updatePost(post, postUpdateRequestDto, newImgUrl);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/posts/{post_id}")
    public ResponseEntity deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails,
            @PathVariable Long post_id
    ) {
        Post post = postRepository.findById(post_id).orElse(null);
        if(post == null || !userDetails.getUsername().equals(post.getCreatedBy())) {
            return ResponseEntity.badRequest().build(); //Todo 메시지를 전송하는 방식으로
        }
        fileUploadService.removeImage(post.getImgUrl());
        postService.deletePost(post);
        return ResponseEntity.ok().build();
    }

}
