package com.gitlog.repository;

import com.gitlog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByAccountId(Long account_id);
    List<Post> findAllById(Long post_id);
    List<Post>findAllByAccount_Nickname(String nickname);
}
