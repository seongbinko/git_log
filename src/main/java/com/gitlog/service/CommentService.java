package com.gitlog.service;

import com.gitlog.dto.CommentRequestDto;
import com.gitlog.model.Account;
import com.gitlog.model.Comment;
import com.gitlog.model.Post;
import com.gitlog.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;

    public void saveComment(Post post, CommentRequestDto commentRequestDto, Account account) {
        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .build();
        Comment newComment = commentRepository.save(comment);
        newComment.addPostAndAccount(post, account);
    }

    public void updateComment(Comment comment, CommentRequestDto commentRequestDto) {
        comment.updateComment(commentRequestDto);
    }

    public void deleteComment(Comment comment, Post post, Account account) {
        comment.removePostAndAccount(post, account);
        commentRepository.delete(comment);
    }
}
