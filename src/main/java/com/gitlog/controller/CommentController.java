package com.gitlog.controller;

import com.gitlog.config.UserDetailsImpl;
import com.gitlog.dto.CommentRequestDto;
import com.gitlog.dto.CommentResponseDto;
import com.gitlog.model.Account;
import com.gitlog.model.Comment;
import com.gitlog.model.Post;
import com.gitlog.repository.AccountRepository;
import com.gitlog.repository.CommentRepository;
import com.gitlog.repository.PostRepository;
import com.gitlog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final AccountRepository accountRepository;

    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity readCommentsByPostId(@PathVariable Long postId) {
        Post post = postRepository.findById(postId).orElse(null);
        if(post == null) {
            return ResponseEntity.badRequest().build();
        }
        List<Comment> comments = commentRepository.findByPostIdOrderByCreatedAtDesc(postId);
        List<CommentResponseDto> toList = comments.stream().map(
                comment -> new CommentResponseDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getCreatedBy())).collect(Collectors.toList());

        return ResponseEntity.ok().body(toList);
    }

    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity saveComment(@PathVariable Long postId,
                                      @AuthenticationPrincipal UserDetailsImpl userDetails,
                                      @RequestBody CommentRequestDto commentRequestDto) {
        Post post = postRepository.findById(postId).orElse(null);
        if(post == null) {
            return ResponseEntity.badRequest().build();
        }
        commentService.saveComment(post, commentRequestDto, userDetails.getAccount());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/posts/*/comments/{commentId}")
    public ResponseEntity updateComment(@PathVariable Long commentId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails,
                                        @RequestBody CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        String nickname = comment.getCreatedBy();

        if(comment == null || !nickname.equals(userDetails.getAccount().getNickname())) {
            return ResponseEntity.badRequest().build();
        }
        commentService.updateComment(comment, commentRequestDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity deleteComment(@PathVariable Long postId, @PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Comment comment = commentRepository.findById(commentId).orElse(null);
        Post post = postRepository.findById(postId).orElse(null);

        String nickname = comment.getCreatedBy();
        if(comment == null || !nickname.equals(userDetails.getAccount().getNickname())) {
            return ResponseEntity.badRequest().build();
        }

        Account account = accountRepository.findByNickname(nickname).orElse(null);
        commentService.deleteComment(comment, post, account);
        return ResponseEntity.ok().build();
    }
}
