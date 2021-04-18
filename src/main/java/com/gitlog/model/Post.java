package com.gitlog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gitlog.config.UserDetailsImpl;
import com.gitlog.dto.PostRequestDto;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected로 기본생성자 생성
@AllArgsConstructor
@ToString(of = {"id", "content", "imgUrl"})
public class Post extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String content;

    private String imgUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
//    @JsonIgnore
    private Account account;

    @OneToMany(mappedBy = "post")
    @Builder.Default
    // @JsonIgnore entity를 직접 노출할 경우 필요
    List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    @Builder.Default
    // @JsonIgnore entity를 직접 노출할 경우 필요
    List<Heart> hearts = new ArrayList<>();

    public void addAccount(Account account){
        this.account = account;
    }

    public void update(PostRequestDto postRequestDto, String imgUrl){
        this.content = postRequestDto.getContent();
        this.imgUrl = imgUrl;
    }
    public void update(String content, String imgUrl){
        this.content = content;
        this.imgUrl = imgUrl;
    }
    public void deletePost(Post post){
        post.getAccount().getPosts().remove(this);
        post.getComments().removeAll(this.comments);
        post.getHearts().removeAll(this.hearts);
        this.account = null;
        this.comments.clear();
        this.hearts.clear();
    }
}