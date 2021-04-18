package com.gitlog.service;

import com.gitlog.config.UserDetailsImpl;
import com.gitlog.model.Account;
import com.gitlog.model.Heart;
import com.gitlog.model.Post;
import com.gitlog.repository.AccountRepository;
import com.gitlog.repository.HeartRepository;
import com.gitlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class HeartService {
    private final HeartRepository heartRepository;
    private final PostRepository postRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public ResponseEntity<String> saveHeart(Long post_id, Account account){
        Post post = postRepository.findById(post_id).orElse(null);
        boolean heartExist = heartRepository.existsByPostAndAccount(post, account);
        if ( post == null || heartExist ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Heart heart = Heart.builder()
                .isHeart(true)
                .build();

        Heart newHeart = heartRepository.save(heart);
        newHeart.addPostAndAccount(post, account);
        return null;
    }

    @Transactional
    public ResponseEntity<String> cancelHeart(Long post_id, UserDetailsImpl userDetails){
        Post post = postRepository.findById(post_id).orElse(null);
        Account account = accountRepository.findByNickname(userDetails.getUsername()).orElse(null);
        boolean exist = heartRepository.existsByPostAndAccount(post, account);
        if (post == null || !exist){
            return new ResponseEntity<>("잘못된 요청입니다.", HttpStatus.BAD_REQUEST);
        }
        Heart heart = heartRepository.findByPostAndAccount(post, account);
        heart.cancelHeart(post, account);
        heartRepository.delete(heart);
        return new ResponseEntity<>("좋아요를 취소 하였습니다.", HttpStatus.OK);
    }
}
