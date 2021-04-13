package com.gitlog.repository;

import com.gitlog.model.Account;
import com.gitlog.model.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAccountOrderByCreatedAtDesc(Account account);

//    @Override
//    @EntityGraph(attributePaths = {"account"})
//    Optional<Post> findById(Long id);
}
