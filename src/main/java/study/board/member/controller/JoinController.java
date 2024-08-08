package study.board.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import study.board.member.dto.MemberJoinDto;
import study.board.member.exception.AlreadyExistsException;
import study.board.member.service.JoinService;

@Controller
@RequiredArgsConstructor
public class JoinController {
    private final JoinService joinService;

    @GetMapping("/signup")
    public String joinForm(@ModelAttribute MemberJoinDto MemberJoinDto){
        return "signupForm";
    }

    @PostMapping("/signup")
    public String join (@Validated @ModelAttribute MemberJoinDto memberJoinDto, BindingResult bindingResult, Model model){
        //System.out.println("memberJoinDto = " + memberJoinDto);

        if (bindingResult.hasErrors()) return "signupForm";

        if(!memberJoinDto.getPassword().equals(memberJoinDto.getPassword2())){
            model.addAttribute("errorMessage","비밀번호가 다릅니다.");
            return "signupForm";
        }

        try {
            joinService.joinProcess(memberJoinDto);
            return "redirect:/";

        }catch (AlreadyExistsException e){
            model.addAttribute("errorMessage",e.getMessage());
            return "signupForm";
        }

    }
}
