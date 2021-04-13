package com.gitlog.service;

import com.gitlog.config.UserDetailsImpl;
import com.gitlog.dto.PostRequestDto;
import com.gitlog.dto.PostResponseDto;
import com.gitlog.model.Account;
import com.gitlog.model.Post;
import com.gitlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;
    //게시글 가져오기
    public Page<PostResponseDto> getPost(int page, int size){
        PageRequest pageRequest = PageRequest.of(page -1, size , Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> posts = postRepository.findAll(pageRequest);
        Page<PostResponseDto> toMap = posts.map(post -> new PostResponseDto(post.getId(), post.getContent(), post.getImgUrl(), post.getCreatedBy()));
        return toMap;
    }

    //게시글 쓰기
    @Transactional
    public Post createPost(PostRequestDto postRequestDto, Account account){
        Post post = Post.builder().content(postRequestDto.getContent()).imgUrl(postRequestDto.getImgUrl()).build();
        Post newPost = postRepository.save(post);
        newPost.addAccount(account);
        return newPost;

    }
    //게시글 수정
    @Transactional
    public void update(Long post_id, PostRequestDto postRequestDto){
        Post post = postRepository.findById(post_id).orElseThrow(() -> new IllegalArgumentException("없는 게시글 아이디 입니다."));
        post.update(postRequestDto);
    }
    //게시글 삭제
    @Transactional
    public void deletePost(Long post_id){
        postRepository.deleteById(post_id);
    }
}
