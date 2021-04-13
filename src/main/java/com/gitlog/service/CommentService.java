package com.gitlog.service;

import com.gitlog.dto.CommentRequestDto;
import com.gitlog.model.Account;
import com.gitlog.model.Comment;
import com.gitlog.model.Post;
import com.gitlog.repository.CommentRepository;
import com.gitlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;


    public HashMap<String, Object> readComment(Long post_id){
        List<Comment> comments = commentRepository.findAllByPostIdOrderByCreatedAtDesc(post_id);
        HashMap<String, Object> map = new HashMap<>();
        map.put("comments", comments);
        return map;
    }

//    public void writeComment(Long post_id, CommentRequestDto commentRequestDto, Account account){
//        String content = commentRequestDto.getContent();
//        System.out.println(content);
//        List<Post> allData = postRepository.findAllById(post_id);
//        for (Post post : allData) {
//            Comment comment = new Comment(content, post);
//            commentRepository.save(comment);
//        }
//    }
}
