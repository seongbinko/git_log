package com.gitlog.repository;

import com.gitlog.model.Comment;
import com.gitlog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
