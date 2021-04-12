package com.gitlog.service;

import com.gitlog.model.Account;
import com.gitlog.model.Heart;
import com.gitlog.model.Post;
import com.gitlog.repository.HeartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class HeartService {

    private final HeartRepository heartRepository;

    public void saveHeart(Post post, Account account) {
        Heart heart = Heart.builder()
                .isHeart(true)
                .build();

        Heart newHeart = heartRepository.save(heart);
        newHeart.addPostAndAccount(post, account);
    }

    public void cancelHeart(Heart heart, Post post, Account account) {
        heart.cancelHeart(post, account);
        heartRepository.delete(heart);
    }
}
