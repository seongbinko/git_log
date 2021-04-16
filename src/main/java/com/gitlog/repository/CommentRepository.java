package com.gitlog.repository;

import com.gitlog.model.Account;
import com.gitlog.model.Comment;
import com.gitlog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByPostIdOrderByCreatedAtDesc(Long post_id);
    List<Comment> findByIdAndAccount_Nickname(Long comment_id, String Nickname);
    void deleteAllByPost(Post post);
}
