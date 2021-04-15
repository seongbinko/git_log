package com.gitlog.service;

import com.amazonaws.services.s3.transfer.Upload;
import com.gitlog.config.UserDetailsImpl;
import com.gitlog.config.uploader.Uploader;
import com.gitlog.dto.AccountResponseDto;
import com.gitlog.dto.CommentResponseDto;
import com.gitlog.dto.PostRequestDto;
import com.gitlog.dto.PostResponseDto;
import com.gitlog.model.Account;
import com.gitlog.model.Comment;
import com.gitlog.model.Post;
import com.gitlog.repository.AccountRepository;
import com.gitlog.repository.CommentRepository;
import com.gitlog.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostService {
    private final AccountRepository accountRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final Uploader uploader;

    //게시글 가져오기
    public Page<PostResponseDto> getPost(int page, int size, UserDetailsImpl userDetails){
        PageRequest pageRequest = PageRequest.of(page -1, size , Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> posts = postRepository.findAll(pageRequest);
        Account account = accountRepository.findByNickname(userDetails.getUsername()).orElse(null);
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
                                comment.getCreatedBy(),
                                new AccountResponseDto(comment.getAccount().getProfileImgUrl())
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
    public ResponseEntity createPost(MultipartFile file, String content, Account account)throws IOException {
        String imgUrl = uploader.upload(file, "static");
        Post post = Post.builder()
                .content(content)
                .imgUrl(imgUrl)
                .build();
        Post newPost = postRepository.save(post);
        newPost.addAccount(account);
        return new ResponseEntity<>("성공적으로 저장되었습니다", HttpStatus.OK);

    }
    //게시글 수정
    @Transactional
    public ResponseEntity updatePost(Long post_id, MultipartFile file, String content, UserDetailsImpl userDetails) throws IOException {
        String imgUrl = uploader.upload(file,"static");
        Post post = postRepository.findById(post_id).orElse(null);
        if (post.getAccount().getNickname() != userDetails.getUsername()){
            return new ResponseEntity<>("다른 사용자의 게시글을 수정할 수 없습니다.",HttpStatus.BAD_REQUEST);
        }
        if (post == null){
            return new ResponseEntity<>("해당 게시글은 없는 게시글입니다.", HttpStatus.BAD_REQUEST);
        }

        post.update(imgUrl, content);
        return new ResponseEntity<>("성공적으로 수정하였습니다", HttpStatus.OK);
    }
    //게시글 삭제
    @Transactional
    public ResponseEntity<String> deletePost(Long post_id){
        Post post = postRepository.findById(post_id).orElse(null);
        if (post == null){
            return new ResponseEntity<>("해당 게시글은 없는 게시글입니다.", HttpStatus.BAD_REQUEST);
        }
        postRepository.deleteById(post_id);
        return new ResponseEntity<>("성공적으로 삭제하였습니다", HttpStatus.OK);
    }
}
