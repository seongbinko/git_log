package com.gitlog.model;

import com.gitlog.dto.CommentRequestDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED) // protected로 기본생성자 생성
@AllArgsConstructor
@ToString(of = {"id", "content"})
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public void addPostAndAccount(Post post, Account account) {
        this.post = post;
        this.account = account;
        post.getComments().add(this);
        account.getComments().add(this);
    }

    public void updateComment(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }

    public void removePostAndAccount(Post post, Account account) {
        post.getComments().remove(this);
        account.getComments().remove(this);
        this.post = null;
        this.account = null;
    }
}
