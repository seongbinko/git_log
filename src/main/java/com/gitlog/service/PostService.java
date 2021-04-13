package com.gitlog.service;

import com.gitlog.dto.PostRequestDto;
import com.gitlog.model.Account;
import com.gitlog.model.Post;
import com.gitlog.repository.CommentRepository;
import com.gitlog.repository.HeartRepository;
import com.gitlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final HeartRepository heartRepository;

    public Post savePost(Account account, PostRequestDto postRequestDto) {
        Post post = Post.builder()
                .content(postRequestDto.getContent())
                .imgUrl(postRequestDto.getImgUrl())
                .build();
        Post newPost = postRepository.save(post);
        newPost.addAccount(account);

        return newPost;
    }

    public void updatePost(PostRequestDto postRequestDto, Post post) {
        post.updatePost(postRequestDto);
    }

    public void deletePost(Post post) {
        //Todo 객체지향 관점으로 접근할 지 이런식으로 할지 고민중 comment 삭제와 heart 삭제와 다름
        post.deletePost(post);
        commentRepository.deleteAllByPost(post);
        heartRepository.deleteAllByPost(post);
        postRepository.delete(post);
    }
}
