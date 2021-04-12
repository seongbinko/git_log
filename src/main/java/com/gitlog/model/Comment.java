package com.gitlog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gitlog.dto.PostRequestDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected로 기본생성자 생성
@AllArgsConstructor
@ToString(of = {"id", "content"})
public class Comment extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    @JsonIgnore
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    public Comment(String content, Post post){
        this.content = content;
        this.post = post;
    }

    public void addAccount(Account account){
        this.account = account;
    }

}
