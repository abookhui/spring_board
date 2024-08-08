package study.board.post.dto;

import jakarta.persistence.Lob;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder

public class PostListDto {

    private Long postId;
    private String title;


    private String content;
    private String  createTime;
    private String username;


    public PostListDto(Long postId, String title, String content, String createTime, String username) {
        this.postId = postId;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.username = username;
    }
}
