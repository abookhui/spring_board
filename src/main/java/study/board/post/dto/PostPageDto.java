package study.board.post.dto;

import jakarta.persistence.Lob;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PostPageDto {

    private Long postId;
    private String title;
    private String content;
    private String createTime;
    private String username;

}
