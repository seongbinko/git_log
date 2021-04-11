package com.gitlog.service;

import com.gitlog.dto.PostRequestDto;
import com.gitlog.model.Comment;
import com.gitlog.model.Post;
import com.gitlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    public Page<Post> getPost(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return postRepository.findAll(pageable);
    }

    public void createPost(PostRequestDto postRequestDto){
        String imgUrl = postRequestDto.getImgUrl();
        String content= postRequestDto.getContent();
        Post post = new Post(imgUrl, content);
        postRepository.save(post);
    }

    @Transactional
    public void update(Long post_id, PostRequestDto postRequestDto){
        Post post = postRepository.findById(post_id).orElseThrow(() -> new IllegalArgumentException("없는 게시글 아이디 입니다."));
        post.update(postRequestDto);
    }

    @Transactional
    public void deletePost(Long post_id){
        postRepository.deleteById(post_id);
    }
}
