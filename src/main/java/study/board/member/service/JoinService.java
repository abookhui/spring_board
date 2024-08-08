package study.board.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import study.board.member.dto.MemberJoinDto;
import study.board.member.entity.Member;
import study.board.member.exception.AlreadyExistsException;
import study.board.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class JoinService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void joinProcess(MemberJoinDto memberJoinDto){
        String username = memberJoinDto.getUsername();
        String password = memberJoinDto.getPassword();
        String email = memberJoinDto.getEmail();
        String nickname = memberJoinDto.getNickname();

        boolean isExistUsername = memberRepository.existsByUsername(username);
        boolean isExistEmail = memberRepository.existsByEmail(email);

        if(isExistUsername) {
            throw new AlreadyExistsException("이미 존재하는 아이디 입니다.");
            // 이미 있는 아이디
        }
        if(isExistEmail){
            throw new AlreadyExistsException("이미 존재하는 이메일 입니다.");
        }
        if(memberRepository.existsByNickname(nickname)){
            throw new AlreadyExistsException("이미 존재하는 별명 입니다.");
        }



        Member saveMember = Member.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .nickname(nickname)
                .role("ROLE_USER")
                .createDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();

        memberRepository.save(saveMember);
    }

}
