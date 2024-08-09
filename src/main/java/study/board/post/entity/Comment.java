package study.board.post.entity;


import jakarta.persistence.*;
import lombok.*;
import study.board.member.entity.Member;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="comment_id")
    private Long id;

    @Column(nullable = false,columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member writer;

    @Column(nullable = false)
    private String createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id",nullable = false)
    private Post post;




}
