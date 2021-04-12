package com.gitlog.model;

import com.gitlog.dto.PostRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
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
    private Account account;

    @OneToMany(mappedBy = "post")
    @Builder.Default
    // @JsonIgnore entity를 직접 노출할 경우 필요
    List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post")
    @Builder.Default
    // @JsonIgnore entity를 직접 노출할 경우 필요
    List<Heart> hearts = new ArrayList<>();

    public void addAccount(Account account) {
        this.account = account;
    }

    public void updatePost(PostRequestDto postRequestDto) {
        this.content = postRequestDto.getContent();
        this.imgUrl = postRequestDto.getImgUrl();
    }

    public void removeCommentsAndHeartsAndAccount(Post post) {
        this.comments.removeAll(post.getComments());
        this.hearts.removeAll(post.getHearts());
        post.getAccount().getPosts().remove(this);

        this.account = null;
    }
}
