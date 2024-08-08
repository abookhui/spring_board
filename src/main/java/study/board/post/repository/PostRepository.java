package study.board.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import study.board.post.dto.PostListDto;
import study.board.post.entity.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post,Long> {
    Optional<Post> findById (Long id);

    @Query("SELECT new study.board.post.dto.PostListDto(p.id, p.title, p.content, p.createTime, p.writer.username) " +
            "FROM Post p")

    List<PostListDto> findPostListDtos();
}
