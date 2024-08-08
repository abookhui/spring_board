package study.board.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import study.board.member.dto.MemberUserDetails;
import study.board.member.entity.Member;
import study.board.member.repository.MemberRepository;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member byUsername = memberRepository.findByUsername(username);

        if(byUsername != null){
            return new MemberUserDetails(byUsername);
        }else{
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

    }


}
