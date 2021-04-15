package com.gitlog.service;

import com.gitlog.dto.CommentRequestDto;
import com.gitlog.model.Account;
import com.gitlog.model.Comment;
import com.gitlog.model.Post;
import com.gitlog.repository.CommentRepository;
import com.gitlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    public ResponseEntity<String> writeComment(Long post_id, CommentRequestDto commentRequestDto, Account account){
        Post post = postRepository.findById(post_id).orElse(null);
        if (post == null){
            return new ResponseEntity<>("해당 게시글을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);
        }
        Comment comment = Comment.builder().content(commentRequestDto.getContent())
                .post(post)
                .account(account)
                .build();
        Comment newComment = commentRepository.save(comment);
        return new ResponseEntity<>("댓글 작성 완료", HttpStatus.OK);
    }
}
