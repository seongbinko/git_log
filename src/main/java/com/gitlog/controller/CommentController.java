package com.gitlog.controller;

import com.gitlog.dto.CommentRequestDto;
import com.gitlog.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

//    @PostMapping("/api/{post_id}/comments")
//    public void writeComment(@PathVariable Long post_id, CommentRequestDto commentRequestDto){
//        commentService.writeComment(post_id, commentRequestDto);
//    }

}
