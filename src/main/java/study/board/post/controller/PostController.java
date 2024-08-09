package study.board.post.controller;

import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import study.board.member.dto.MemberUserDetails;
import study.board.post.dto.*;
import study.board.post.entity.Post;
import study.board.post.repository.CommentRepository;
import study.board.post.repository.PostRepository;
import study.board.post.service.CommentService;
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
    private final CommentRepository commentRepository;
    private final CommentService commentService;

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



    @GetMapping("/edit/{postId}")
    public String editForm(@PathVariable Long postId,Model model,Authentication auth){
        MemberUserDetails principal = (MemberUserDetails) auth.getPrincipal();

        Optional<Post> byId = postRepository.findById(postId);

        if(byId.isEmpty()) {
            return "post/postList";  // 반환값 없으면 처리
        }

        Post post = byId.get();
        PostEditDto build = PostEditDto.builder()  // 게시판 아이디
                .username(post.getWriter().getUsername())
                .postId(postId)
                .title(post.getTitle())
                .content(post.getContent())
                .build();

        if(principal.getUsername().equals(build.getUsername())){
            model.addAttribute("post",build);

            return "post/pageEdit";
        }

        return null;
    }

    @PostMapping("/edit/{postId}")
    public String edit(@PathVariable Long postId,@ModelAttribute PostEditDto postEditDto){
        postService.editPost(postEditDto);

        return "redirect:/post/"+postId;
    }

    @PostMapping("/delete/{postId}")
    public String delete(@PathVariable Long postId,Authentication auth){
        MemberUserDetails principal = (MemberUserDetails) auth.getPrincipal();

        try{
            postService.deletePost(postId,principal.getUsername());

            return "redirect:/post";
        }catch (RuntimeException e){
            return "/";
        }
    }
    @GetMapping("/{postId}")
    public String postByid(@PathVariable Long postId, Model model,Authentication auth){
        model.addAttribute("postForm",new CommentCreateDto());

        return createPageModel(postId, auth, model);

    }



    @PostMapping("/{postId}/comment")
    public String comment(@PathVariable Long postId,@ModelAttribute("postForm") CommentCreateDto req,
            BindingResult bindingResult, Authentication auth,Model model){

        if(req.getContent() == null || req.getContent().trim().isEmpty()){
            bindingResult.rejectValue("content","content.empty", "내용을 입력해 주세요.");
            return createPageModel(postId, auth, model);
        }

        try {
            commentService.addComment(auth.getName(),req,postId);
            return "redirect:/post/"+postId;
        }catch (RuntimeException e){
            return null;
        }

    }

    @PostMapping("/{postId}/comment/{commentId}/delete")
    public String deleteComment(@PathVariable Long postId, @PathVariable Long commentId,Authentication auth){
        //System.out.println("postId = " + postId + " commentId = "+ commentId);
        MemberUserDetails principal = (MemberUserDetails) auth.getPrincipal();
        Long redirectId = commentService.deleteComment(principal.getUsername(), postId, commentId);


        return "redirect:/post/"+redirectId;
    }


    private String createPageModel(Long postId, Authentication auth, Model model) {
        // 기존 데이터와 함께 다시 폼 페이지를 렌더링
        Optional<Post> byId = postRepository.findById(postId);

        if(byId.isEmpty()) {
            return "post/postList";
        }
        MemberUserDetails principal = (MemberUserDetails) auth.getPrincipal();
        Post post = byId.get();

        PostPageDto build = PostPageDto.builder()
                .postId(postId)
                .content(post.getContent())
                .title(post.getTitle())
                .createTime(post.getCreateTime())
                .username(post.getWriter().getUsername())
                .build();


        //boolean isAuthor = build.getUsername().equals(principal.getUsername());
        List<CommentCreateDto> comments = commentRepository.findByPostId(postId);

        model.addAttribute("currentUsername",principal.getUsername());
        model.addAttribute("post",build);
        model.addAttribute("comments",comments);

        return "post/page";
    }



}
