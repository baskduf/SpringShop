package my.shop.member;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberSignupForm {

    @NotEmpty
    @Size(max = 10)
    private String firstName;

    @NotEmpty
    @Size(max = 10)
    private String lastName;

    @NotEmpty
    private String phone_number;

    @NotEmpty
    private String email;

    @NotEmpty
    private String password;

    @NotEmpty
    private String confirm_password;

}
