package my.shop.member;

import jakarta.persistence.*;
import lombok.Data;
import my.shop.item.Item;

import java.util.LinkedList;
import java.util.List;

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

    @ManyToMany(cascade = CascadeType.ALL)
    List<Item> items = new LinkedList<>();

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    public void order(Item item) {
        items.add(item);
    }

    public static Member createMember(MemberSignupForm memberSignupForm, MemberRole role) {
        Member member = new Member();
        member.setEmail(memberSignupForm.getEmail());
        member.setName(memberSignupForm.getFirstName() + memberSignupForm.getLastName());
        member.setPassword(memberSignupForm.getPassword()); // password encoding 필요
        member.setPhone_number(memberSignupForm.getPhone_number());
        member.setMemberRole(role);
        return member;
    }

}
