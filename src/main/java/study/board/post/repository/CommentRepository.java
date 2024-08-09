package study.board.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import study.board.post.dto.CommentCreateDto;
import study.board.post.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("SELECT new study.board.post.dto.CommentCreateDto("
            + "c.id, c.content, c.writer.nickname, c.createTime, c.post.id,c.writer.username) "
            + "FROM Comment c WHERE c.post.id = :postId")
    List<CommentCreateDto> findByPostId(Long postId);


    Optional<Comment> findById(Long id);
}
