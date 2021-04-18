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

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final AccountRepository accountRepository;


    @Transactional
    public ResponseEntity<String> writeComment(Long post_id, CommentRequestDto commentRequestDto, UserDetailsImpl userDetails){
        Post post = postRepository.findById(post_id).orElse(null);
        Account account = accountRepository.findByNickname(userDetails.getUsername()).orElse(null);
        if (post == null){
            return new ResponseEntity<>("해당 게시글을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);
        }
        System.out.println(commentRequestDto.getContent());
        Comment comment = Comment.builder()
                .content(commentRequestDto.getContent())
                .build();
        Comment newComment = commentRepository.save(comment);
        newComment.addPostAndAccount(post, account);
        return new ResponseEntity<>("댓글 작성 완료", HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> updateComment(Long post_id, Long comment_id, CommentRequestDto commentRequestDto, Account account){
        Post post = postRepository.findById(post_id).orElse(null);
        if (commentRequestDto.getContent() == null || commentRequestDto.getContent().isEmpty()){
            return new ResponseEntity<>("변경할 내용을 적어 주세요", HttpStatus.BAD_REQUEST);
        }
        if (post == null){
            return new ResponseEntity<>("없는 게시글입니다.", HttpStatus.BAD_REQUEST);
        }
        Comment comment = commentRepository.findById(comment_id).orElse(null);
        if (comment == null){
            return new ResponseEntity<>("없는 댓글입니다", HttpStatus.BAD_REQUEST);
        }
        if (!comment.getAccount().getNickname().equals(account.getNickname())){
            return new ResponseEntity<>("다른 사용자의 댓글을 수정하실 수 없습니다.",HttpStatus.BAD_REQUEST);
        }
        comment.updateComment(commentRequestDto.getContent());
        return new ResponseEntity<>("성공적으로 수정 하였습니다.",HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> deleteComment(Long post_id, Long comment_id, Account account){
        Post post = postRepository.findById(post_id).orElse(null);
        if (post == null){
            return new ResponseEntity<>("없는 게시글입니다.", HttpStatus.BAD_REQUEST);
        }
        Comment comment = commentRepository.findById(comment_id).orElse(null);
        if (comment == null){
            return new ResponseEntity<>("없는 댓글입니다", HttpStatus.BAD_REQUEST);
        }
        if (!comment.getAccount().getNickname().equals(account.getNickname())){
            return new ResponseEntity<>("다른 사용자의 댓글을 삭제하실 수 없습니다.",HttpStatus.BAD_REQUEST);
        }
        comment.removePostAndAccount(post, account);
        commentRepository.deleteById(comment_id);
        return new ResponseEntity<>("성공적으로 삭제 하였습니다.", HttpStatus.OK);
    }
}
