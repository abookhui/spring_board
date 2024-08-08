package study.board.member.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import study.board.member.entity.Member;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class MemberUserDetails implements UserDetails {

    private final Member member;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();

//        collection.add(new GrantedAuthority() {
//            @Override
//            public String getAuthority() {
//                return member.getRole();
//            }
//        });

        // -> 람다로 변경
        collection.add(()->{
            return member.getRole();
        });


        return collection; // role 값 반환
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    public String getEmail(){
        return member.getEmail();
    }
    public String getNickname(){
        return member.getNickname();
    }
    public String getCreateDate(){
        return member.getCreateDate();
    }
    public String getRole(){
        return member.getRole();
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
