package com.gitlog.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected로 기본생성자 생성
@AllArgsConstructor
@ToString(of = {"id", "isHeart"})
public class Heart extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "heart_id")
    private Long id;

    private boolean isHeart;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void addPostAndAccount(Post post, Account account) {
        this.account = account;
        this.post = post;
        post.getHearts().add(this);
        account.getHearts().add(this);
    }

    public void cancelHeart(Post post, Account account) {
        post.getHearts().remove(this);
        account.getHearts().remove(this);
        this.post = null;
        this.account = null;
    }
}
