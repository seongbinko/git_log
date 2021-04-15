package com.gitlog.model;

import com.gitlog.dto.ProfileRequestDto;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)// protected로 기본생성자 생성
@AllArgsConstructor
@ToString(of = {"id", "nickname", "email", "bio", "profileImgUrl", "githubUrl"})
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

    private String profileImgUrl;

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

    public void updateProfile(ProfileRequestDto profileRequestDto, String profileImgUrl) {
        this.password = StringUtils.hasText(profileRequestDto.getPassword()) ? profileRequestDto.getPassword() : this.password;
        this.bio = StringUtils.hasText(profileRequestDto.getBio()) ? profileRequestDto.getBio() : this.bio;
        this.profileImgUrl = StringUtils.hasText(profileImgUrl) ? profileImgUrl : this.profileImgUrl;
        this.githubUrl = StringUtils.hasText(profileRequestDto.getGithubUrl()) ? profileRequestDto.getGithubUrl() :this.githubUrl;
    }
}
