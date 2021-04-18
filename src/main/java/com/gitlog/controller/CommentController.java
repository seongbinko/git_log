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

    // 댓글 다는 부분
    @PostMapping("/api/posts/{post_id}/comments")
    public ResponseEntity<String> writeComment(@PathVariable Long post_id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.writeComment(post_id, commentRequestDto, userDetails);
    }
    //댓글 수정
    @PutMapping("/api/posts/{post_id}/comments/{comment_id}")
    public ResponseEntity<String> updateComment(@PathVariable Long post_id, @PathVariable Long comment_id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return commentService.updateComment(post_id, comment_id, commentRequestDto, userDetails.getAccount());
    }

    // 댓글 삭제
    @DeleteMapping("/api/posts/{post_id}/comments/{comment_id}")
    public void deleteComment(@PathVariable Long post_id, @PathVariable Long comment_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        commentService.deleteComment(post_id, comment_id, userDetails.getAccount());
    }
}
