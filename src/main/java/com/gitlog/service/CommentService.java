package com.gitlog.service;

import com.gitlog.dto.CommentRequestDto;
import com.gitlog.model.Account;
import com.gitlog.model.Comment;
import com.gitlog.model.Post;
import com.gitlog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public void writeComment(Post post, CommentRequestDto commentRequestDto, Account account){
        String content = commentRequestDto.getComment();
        Comment comment = new Comment(content, post, account);
        commentRepository.save(comment);
    }

}
