package study.board.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import study.board.member.dto.MemberUpdateDto;
import study.board.member.entity.Member;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member,Long> {
    Member findByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);

    @Query("SELECT new study.board.member.dto.MemberUpdateDto (m.nickname,m.username) FROM Member m"
    + " WHERE m.username = :username")
    MemberUpdateDto findUpdateDtoByUsername(String username);


}
