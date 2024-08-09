package study.board.post.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import study.board.member.entity.Member;
import study.board.post.entity.Comment;
import study.board.post.entity.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateDto {

    @NotEmpty
    private Long commentId;
    @NotEmpty
    private String content;
    @NotEmpty
    private String nickname;
    @NotEmpty
    private String createTime;
    @NotEmpty
    private Long postId;
    private String username;

    public Comment toEntity(Member member, Post post){
        return  Comment.builder()
                .content(content)
                .writer(member)
                .post(post)
                .createTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm")))
                .build();
    }
}
