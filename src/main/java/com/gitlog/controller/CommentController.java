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

//    @GetMapping("/api/posts/{post_id}/comments")
//    public HashMap<String, Object> getComment(@PathVariable Long post_id){
//        return commentService.readComment(post_id);
//
//    }
    @PostMapping("/api/posts/{post_id}/comments")
    public ResponseEntity writeComment(@PathVariable Long post_id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

        return commentService.writeComment(post_id, commentRequestDto, userDetails.getAccount());
    }
//    @PutMapping("/api/posts/{post_id}/{comment_id}")
//
//    @DeleteMapping("/api/posts/{post_id}/comments/{comment_id}")
//    public void deleteComment(@PathVariable Long post_id, @PathVariable Long comment_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
//        commentService.deleteComment(post_id, comment_id, userDetails);
//    }

}
