package my.shop.member;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "member")
@Data
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String phone_number;

    private String email;

    private String password;

    public static Member createMember(MemberSignupForm memberSignupForm) {
        Member member = new Member();
        member.setEmail(memberSignupForm.getEmail());
        member.setName(memberSignupForm.getFirstName() + memberSignupForm.getLastName());
        member.setPassword(memberSignupForm.getPassword()); // password encoding 필요
        member.setPhone_number(memberSignupForm.getPhone_number());
        return member;
    }

}
