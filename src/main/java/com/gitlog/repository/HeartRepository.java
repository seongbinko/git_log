package com.gitlog.repository;

import com.gitlog.model.Account;
import com.gitlog.model.Heart;
import com.gitlog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface HeartRepository extends JpaRepository<Heart, Long> {

    boolean existsByPostAndAccount(Post post, Account account);

    Heart findByPostAndAccount(Post post, Account account);

    void deleteAllByPost(Post post);
}
