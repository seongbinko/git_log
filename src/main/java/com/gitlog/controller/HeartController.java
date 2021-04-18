package com.gitlog.controller;

import com.gitlog.config.UserDetailsImpl;
import com.gitlog.service.HeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class HeartController {

    private  final HeartService heartService;

    //좋아요 남기기
    @PostMapping("/api/posts/{post_id}/heart")
    public ResponseEntity<String> saveHeart(@PathVariable Long post_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return heartService.saveHeart(post_id, userDetails.getAccount());
    }

    //좋아요 지우기
    @DeleteMapping("/api/posts/{post_id}/heart")
    public ResponseEntity<String> cancelHeart(@PathVariable Long post_id, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return heartService.cancelHeart(post_id, userDetails);
    }
}
