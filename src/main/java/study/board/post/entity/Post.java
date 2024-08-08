package study.board.post.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
import study.board.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Post {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="post_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)

    @JoinColumn(name="member_id",nullable = false)
    private Member writer;

    @Column(nullable = false)
    private String createTime;

    public void editPost(String title,String content,String createTime){
        this.title = title;
        this.content=content;
        this.createTime=createTime;
    }

}
