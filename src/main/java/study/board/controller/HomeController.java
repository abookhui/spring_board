package study.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import study.board.member.dto.MemberUpdateDto;
import study.board.member.dto.MemberUserDetails;
import study.board.member.repository.MemberRepository;
import study.board.member.service.MemberUpdateService;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberUpdateService memberUpdateService;
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }

    @GetMapping("/mypage")
    public String userPage(Model model){

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberUserDetails userDetails = (MemberUserDetails)principal;

       // System.out.println("user = " + userDetails);

        //MemberUpdateDto byUsername = memberUpdateService.findByUsername(username);
        MemberUpdateDto byUsername = memberRepository.findUpdateDtoByUsername(username);

        //model.addAttribute("username",username);
        model.addAttribute("nickname",byUsername.getNickname());
        model.addAttribute("role",userDetails.getRole() );

        return "mypage";
    }

    @GetMapping("/mypage/update")
    public String updateForm(@ModelAttribute MemberUpdateDto memberUpdateDto, Model model){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        //MemberUpdateDto byUsername = memberUpdateService.findByUsername(username);
        MemberUpdateDto my = memberRepository.findUpdateDtoByUsername(username);


        model.addAttribute("memberUpdateDto",my);

        return "memberUpdate";
    }

    @PostMapping("/mypage/update")
    public String update(@ModelAttribute @Validated MemberUpdateDto memberUpdateDto, BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()){
            return "memberUpdate";
        }

        try {
            memberUpdateService.updateMember(memberUpdateDto);
            return "redirect:/mypage";
        }catch (RuntimeException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "memberUpdate";
        }
    }
}
