package study.board.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import study.board.member.dto.MemberUpdateDto;
import study.board.member.entity.Member;
import study.board.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberUpdateService {

    private final MemberRepository memberRepository;

    public void updateMember(MemberUpdateDto memberUpdateDto){
        Member byUsername = memberRepository.findByUsername(memberUpdateDto.getUsername());

        if(memberRepository.existsByNickname(memberUpdateDto.getNickname())){
            throw new RuntimeException("이미 존재하는 별명 혹은 이전의 별명입니다.");
        }
        if(byUsername == null){
            throw new RuntimeException("회원을 찾을 수 없습니다.");
        }


        byUsername.updateNickname(memberUpdateDto.getNickname());

        memberRepository.save(byUsername);
    }

}
