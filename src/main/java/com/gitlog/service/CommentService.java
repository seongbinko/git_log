package com.gitlog.service;

import com.gitlog.config.UserDetailsImpl;
import com.gitlog.dto.CommentRequestDto;
import com.gitlog.model.Account;
import com.gitlog.model.Comment;
import com.gitlog.model.Post;
import com.gitlog.repository.AccountRepository;
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
    private final AccountRepository accountRepository;


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

    public ResponseEntity<String> updateComment(Long post_id, Long comment_id, Account account){
        Post post = postRepository.findById(post_id).orElse(null);
        if (account == null){
            return new ResponseEntity<>("다른 사용자 댓글을 수정할 수 없습니다",HttpStatus.BAD_REQUEST);
        }else{
            if (post == null){
                return new ResponseEntity<>("없는 게시글입니다",HttpStatus.BAD_REQUEST);
            }else{
                Comment comment = commentRepository.findById(comment_id).orElse(null);
                if (comment == null){
                    return new ResponseEntity<>("없는 댓글입니다",HttpStatus.BAD_REQUEST);
                }
            }
            return new ResponseEntity<>("뎃글 수정 완료", HttpStatus.OK);
        }
    }
}
