package com.gitlog.controller;

import com.gitlog.config.UserDetailsImpl;
import com.gitlog.dto.CommentRequestDto;
import com.gitlog.model.Comment;
import com.gitlog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/api/posts/{post_id}/comments")
    public ResponseEntity<String> writeComment(@PathVariable Long post_id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return commentService.writeComment(post_id, commentRequestDto, userDetails.getAccount());
    }
    @PutMapping("/api/posts/{post_id}/comments/{comment_id}")
    public ResponseEntity<String> updateComment(@PathVariable Long post_id, @PathVariable Long comment_id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(post_id, comment_id, commentRequestDto, userDetails.getAccount());
    }

        @DeleteMapping("/api/posts/{post_id}/comments/{comment_id}")
        public void deleteComment(@PathVariable Long post_id, @PathVariable Long comment_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
            commentService.deleteComment(post_id, comment_id, userDetails.getAccount());
        }

}
