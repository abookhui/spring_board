package study.board.post.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import study.board.member.entity.Member;
import study.board.post.entity.Post;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class PostCreateDto {

    @NotEmpty
    private String title;


    @Size(min = 1, max = 16000)
    @NotEmpty
    private String content;

    public Post toEntity(Member member) {
        return Post.builder()
                .writer(member)
                .title(title)
                .content(content)
                .createTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }

}
