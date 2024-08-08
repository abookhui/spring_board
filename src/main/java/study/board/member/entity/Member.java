package study.board.member.entity;


import jakarta.persistence.*;
import lombok.*;
import study.board.post.entity.Post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="member_id")
    private Long id;

    @Column(unique = true,nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    private String role; // USER, ADMIN

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String createDate;  // 언제 데이터가 들어왔는지

    @OneToMany(mappedBy = "writer" , cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Post> postList = new ArrayList<>();

    public void updateNickname(String nickname){
        this.nickname=nickname;
    }

}
