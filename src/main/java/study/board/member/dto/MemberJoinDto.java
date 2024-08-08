package study.board.member.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberJoinDto {

    @NotEmpty
    @Size(min=2, max = 20)
    private String username;

    @NotEmpty
    @Size(min=4, max = 20)
    private String password;
    @NotEmpty
    @Size(min=4, max = 20)
    private String password2;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min=2,max=10)
    private String nickname;
}
