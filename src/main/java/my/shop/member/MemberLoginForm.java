package my.shop.member;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class MemberLoginForm {

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

}
