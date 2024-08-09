package study.board.post.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.board.member.entity.Member;
import study.board.member.repository.MemberRepository;
import study.board.post.dto.CommentCreateDto;
import study.board.post.entity.Comment;
import study.board.post.entity.Post;
import study.board.post.repository.CommentRepository;
import study.board.post.repository.PostRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;

    public void addComment(String username,CommentCreateDto req,Long postId){
        Member byUsername = memberRepository.findByUsername(username);
        Post post = postRepository.findOneById(postId);

        String content = req.getContent();
        if(content.isEmpty() || content.trim().isEmpty()){
            throw new RuntimeException("댓글을 작성하세요");
        }

        commentRepository.save(req.toEntity(byUsername,post));
    }

    public Long deleteComment(String username,Long postId, Long commentId){
        Optional<Comment> byCommentId = commentRepository.findById(commentId);
        Optional<Post> byPostId = postRepository.findById(postId);

        if(byCommentId.isEmpty() || byPostId.isEmpty()) throw new RuntimeException("존재하지 않는 댓글입니다.");
        Comment comment = byCommentId.get();
        Post post = byPostId.get();

        String commentUsername = comment.getWriter().getUsername();
        Long postGetPostId = post.getId();
        Long commentGetPostId = comment.getPost().getId();

        // 댓글 단 유저 == 현재 유저 , 댓글 포스터 == 포스터
        if(commentUsername.equals(username) && postGetPostId.equals(commentGetPostId)){
            commentRepository.delete(comment);
            return postGetPostId;
        }

        else throw new RuntimeException("잘못된 접근");
    }

}
