package study.board.post.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import study.board.member.dto.MemberUserDetails;
import study.board.post.dto.PostCreateDto;
import study.board.post.dto.PostEditDto;
import study.board.post.dto.PostListDto;
import study.board.post.dto.PostPageDto;
import study.board.post.entity.Post;
import study.board.post.repository.PostRepository;
import study.board.post.service.PostService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @GetMapping
    public String list(Model model){
        List<PostListDto> posts = postRepository.findPostListDtos();

        model.addAttribute("posts",posts);
        return "post/postList";
    }

    @GetMapping("/write")
    public String writeForm(Model model){
        return "post/write";
    }
    @PostMapping("/write")
    public String postWrite(@Validated @ModelAttribute PostCreateDto req, BindingResult bindingResult, Authentication auth, Model model) throws IOException {

        if(bindingResult.hasErrors()){
            return "write";
        }

        Long savePostId = postService.writePost(req, auth.getName(), auth);
        return "redirect:/post/"+savePostId;
    }

    @GetMapping("/{postId}")
    public String postByid(@PathVariable Long postId, Model model){
        Optional<Post> byId = postRepository.findById(postId);

        if(byId.isEmpty()) {
            return "post/postList";  // 반환값 없으면 처리
        }

        Post post = byId.get();
        PostPageDto build = PostPageDto.builder()
                .postId(postId)
                .content(post.getContent())
                .title(post.getTitle())
                .createTime(post.getCreateTime())
                .username(post.getWriter().getUsername())
                .build();
        model.addAttribute("post",build);
        return "post/page";
    }


    @GetMapping("/edit/{postId}")
    public String editForm(@PathVariable Long postId,Model model,Authentication auth){
        MemberUserDetails principal = (MemberUserDetails) auth.getPrincipal();


        Optional<Post> byId = postRepository.findById(postId);

        if(byId.isEmpty()) {
            return "post/postList";  // 반환값 없으면 처리
        }

        Post post = byId.get();
        PostEditDto build = PostEditDto.builder()
                .postId(postId)
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        model.addAttribute("post",build);
        return "post/pageEdit";
    }

    @PostMapping("/edit/{postId}")
    public String edit(@PathVariable Long postId,@ModelAttribute PostEditDto postEditDto){
        postService.editPost(postEditDto);

        return "redirect:/post/"+postId;
    }
}
