package com.gitlog.repository;

import com.gitlog.model.Account;
import com.gitlog.model.Heart;
import com.gitlog.model.Post;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart,Long> {
    boolean existsByPostAndAccount(Post post, Account account);
    void deleteAllByPost(Post post);
    Heart findByPostAndAccount(Post post, Account account);
}
