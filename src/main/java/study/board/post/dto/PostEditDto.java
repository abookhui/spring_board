package study.board.post.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.parameters.P;
import study.board.post.entity.Post;

@Data
@Builder
public class PostEditDto {

    private Long postId;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;

    private String username;


}
