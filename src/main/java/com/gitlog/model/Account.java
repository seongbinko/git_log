package com.gitlog.model;

import com.gitlog.dto.AccountRequestDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)// protected로 기본생성자 생성
@AllArgsConstructor
@ToString(of = {"id", "nickname", "email", "bio", "imgUrl", "githubUrl"})
//password, bio, github_url, img_url
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    private String password;

    private String nickname;

    private String email;

    private String bio;

    private String imgUrl;

    private String githubUrl;

    public Account(String password, String nickname, String email, String bio, String imgUrl, String githubUrl){
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.bio = bio;
        this.imgUrl = imgUrl;
        this.githubUrl = githubUrl;
    }

//    public Account (AccountRequestDto accountRequestDto){
//        this.password = accountRequestDto.getPassword();
//        this.nickname = accountRequestDto.getNickname();
//        this.email = accountRequestDto.getEmail();
//        this.bio = accountRequestDto.getBio();
//        this.githubUrl = accountRequestDto.getGithubUrl();
//        this.imgUrl = accountRequestDto.getImgUrl();
//    }

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    @Builder.Default
    // @JsonIgnore entity를 직접 노출할 경우 필요
    List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "account")
    @Builder.Default
    // @JsonIgnore entity를 직접 노출할 경우 필요
    List<Comment> comments = new ArrayList<>();

}
