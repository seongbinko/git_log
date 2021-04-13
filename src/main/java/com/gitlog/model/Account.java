package com.gitlog.model;

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

    @OneToMany(mappedBy = "account")
    @Builder.Default
    List<Heart> hearts = new ArrayList<>();

}
