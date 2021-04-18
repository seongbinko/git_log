package com.gitlog.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.gitlog.config.UserDetailsImpl;
import com.gitlog.config.uploader.Uploader;
import com.gitlog.dto.AccountResponseDto;
import com.gitlog.dto.CommentResponseDto;
import com.gitlog.dto.PostRequestDto;
import com.gitlog.dto.PostResponseDto;
import com.gitlog.model.Account;
import com.gitlog.model.Post;
import com.gitlog.repository.AccountRepository;
import com.gitlog.repository.CommentRepository;
import com.gitlog.repository.HeartRepository;
import com.gitlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final AccountRepository accountRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final HeartRepository heartRepository;
    private final Uploader uploader;
    private final AmazonS3Client amazonS3Client;

    //게시글 가져오기
    public Page<PostResponseDto> getPost(int page, int size, UserDetailsImpl userDetails){
        PageRequest pageRequest = PageRequest.of(page -1, size , Sort.by(Sort.Direction.DESC, "createdAt"));
        // 페이지로 포스트를 가져 오고
        Page<Post> posts = postRepository.findAll(pageRequest);
        // 바로 내려주는것이 아니라 responseDTO 에 넣어서 필요한 정보만 넣어서 Client 에게 넘겨줄 수 있도록
        Page<PostResponseDto> toMap = posts.map(post -> new PostResponseDto(
                post.getId(),
                post.getContent(),
                post.getImgUrl(),
                post.getCreatedAt(),
                post.getCreatedBy(),
                post.getModifiedAt(),
                post.getComments().stream().map(
                        comment -> new CommentResponseDto(
                                comment.getId(),
                                comment.getContent(),
                                comment.getCreatedAt(),
                                comment.getCreatedBy()
                        )
                ).collect(Collectors.toList()),
                new AccountResponseDto(post.getAccount().getProfileImgUrl()),
                post.getComments().size(),
                post.getHearts().size()
                ));
        return toMap;
    }

    //게시글 쓰기
    @Transactional
    public ResponseEntity<String> createPost(PostRequestDto postRequestDto, Account account)throws IOException {
        if (postRequestDto.getContent()== null || postRequestDto.getContent().isEmpty()){
            return new ResponseEntity<>("게시글을 적어주세요",HttpStatus.BAD_REQUEST);
        }
        else if ( postRequestDto.getPostImg() == null || postRequestDto.getPostImg().isEmpty() ){
            return new ResponseEntity<>("이미지를 올려주세요", HttpStatus.OK);
        }else {
            String imgUrl = uploader.upload(postRequestDto.getPostImg(), "static");
            Post post = Post.builder()
                    .content(postRequestDto.getContent())
                    .imgUrl(imgUrl)
                    .build();
            Post newPost = postRepository.save(post);
            newPost.addAccount(account);
            return new ResponseEntity<>("성공적으로 저장되었습니다", HttpStatus.OK);
        }
    }
    //게시글 수정
    @Transactional
    public ResponseEntity<String> updatePost(Long post_id, PostRequestDto postRequestDto, UserDetailsImpl userDetails) throws IOException {
        if (postRequestDto == null){
            return new ResponseEntity<>("변경하실 게시글이나 사진을 올려주세요", HttpStatus.BAD_REQUEST);
        }

        Post post = postRepository.findById(post_id).orElse(null);

        if (post == null){
            return new ResponseEntity<>("해당 게시글은 없는 게시글입니다.", HttpStatus.BAD_REQUEST);
        }

        if (!post.getAccount().getNickname().equals(userDetails.getUsername())){
            return new ResponseEntity<>("다른 사용자의 게시글을 수정할 수 없습니다.",HttpStatus.BAD_REQUEST);
        }

        if (postRequestDto.getPostImg() == null || postRequestDto.getPostImg().isEmpty()) {
            String imgUrl = post.getImgUrl();
            post.update(postRequestDto, imgUrl);
        }

        if (postRequestDto.getContent() == null){
            String content = post.getContent();
            if (postRequestDto.getPostImg().isEmpty()){
                String imgUrl = post.getImgUrl();
                post.update(content, imgUrl);
            }else {
                String imgUrl = uploader.upload(postRequestDto.getPostImg(), "static");
                post.update(content, imgUrl);
            }
        }

        return new ResponseEntity<>("성공적으로 수정하였습니다", HttpStatus.OK);
    }
    //게시글 삭제
    @Transactional
    public ResponseEntity<String> deletePost(Long post_id, Account account){
        Post post = postRepository.findById(post_id).orElse(null);
        if (post == null){
            return new ResponseEntity<>("해당 게시글은 없는 게시글입니다.", HttpStatus.BAD_REQUEST);
        }
        if (!post.getAccount().getNickname().equals(account.getNickname())){
            return new ResponseEntity<>("다른 사용자의 게시글을 삭제 할 수 없습니다.", HttpStatus.BAD_REQUEST);
        }
        amazonS3Client.deleteObject("hanghae99-gitlog",post.getImgUrl());
        post.deletePost(post);
        commentRepository.deleteAllByPost(post);
        heartRepository.deleteAllByPost(post);
        postRepository.deleteById(post_id);
        return new ResponseEntity<>("성공적으로 삭제하였습니다", HttpStatus.OK);
    }

    public ResponseEntity userStory(String nickname){
        Account account = accountRepository.findByNickname(nickname).orElse(null);
        if (account == null){
            return new ResponseEntity<>("없는 사용자 입니다", HttpStatus.BAD_REQUEST);
        }
        List<Post> posts = postRepository.findAllByAccount_Nickname(nickname);
        List<PostResponseDto> toList = posts.stream().map(
                post -> new PostResponseDto(
                post.getId(),
                post.getContent(),
                post.getImgUrl(),
                post.getCreatedAt(),
                post.getCreatedBy(),
                post.getModifiedAt(),
                post.getComments().stream().map(
                        comment -> new CommentResponseDto(
                                comment.getId(),
                                comment.getContent(),
                                comment.getCreatedAt(),
                                comment.getCreatedBy()
                        )
                ).collect(Collectors.toList()),
                new AccountResponseDto(post.getAccount().getProfileImgUrl()),
                post.getComments().size(),
                post.getHearts().size()
        )).collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("posts",toList);
        return ResponseEntity.ok().body(map);
    }
}
