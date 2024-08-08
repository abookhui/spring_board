package study.board.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import study.board.member.entity.Member;
import study.board.member.repository.MemberRepository;
import study.board.post.dto.PostCreateDto;
import study.board.post.dto.PostEditDto;
import study.board.post.entity.Post;
import study.board.post.repository.PostRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public Long writePost(PostCreateDto req, String username, Authentication auth) throws IOException {
        Member loginMember = memberRepository.findByUsername(username);

        Post savePost = postRepository.save(req.toEntity(loginMember));

        return savePost.getId();
    }

    public void editPost(PostEditDto postEditDto){
        Optional<Post> byId = postRepository.findById(postEditDto.getPostId());

        if(byId.isEmpty()){
            throw new RuntimeException("존재하지 않은 글입니다.");
        }
        Post post = byId.get();

        post.editPost(postEditDto.getTitle(),postEditDto.getContent(), LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));

        postRepository.save(post);

    }
}
