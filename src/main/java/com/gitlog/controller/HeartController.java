package com.gitlog.controller;

import com.gitlog.config.UserDetailsImpl;
import com.gitlog.model.Account;
import com.gitlog.model.Heart;
import com.gitlog.model.Post;
import com.gitlog.repository.HeartRepository;
import com.gitlog.repository.PostRepository;
import com.gitlog.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class HeartController {

    private final HeartService heartService;

    private final PostRepository postRepository;
    private final HeartRepository heartRepository;

    @GetMapping("/api/posts/{post_id}/heart")
    public boolean ReadHeart(@PathVariable Long post_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Post post = postRepository.findById(post_id).orElse(null);
        Account account = userDetails.getAccount();

        Heart heart = heartRepository.findByPostAndAccount(post, account);
        if(heart != null) {
            return true;
        }
        return false;
    }

    @PostMapping("/api/posts/{post_id}/heart")
    public ResponseEntity saveHeart(@PathVariable Long post_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Post post = postRepository.findById(post_id).orElse(null);
        Account account = userDetails.getAccount();
        boolean exists = heartRepository.existsByPostAndAccount(post, account);

        if(post == null || exists) {
            return ResponseEntity.badRequest().build();
        }
        heartService.saveHeart(post, account);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/api/posts/{post_id}/heart")
    public ResponseEntity cancelHeart(@PathVariable Long post_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        Post post = postRepository.findById(post_id).orElse(null);

        Account account = userDetails.getAccount();

        boolean exists = heartRepository.existsByPostAndAccount(post, account);
        if(post == null || !exists) {
            return ResponseEntity.badRequest().build();
        }
        Heart heart = heartRepository.findByPostAndAccount(post, account);
        heartService.cancelHeart(heart, post, account);
        return ResponseEntity.ok().build();
    }
}
