package my.shop.member;

import jakarta.persistence.*;
import lombok.Data;
import my.shop.cart.Cart;
import my.shop.haveitem.HaveItem;
import my.shop.item.Item;

import java.util.ArrayList;
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

    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "member")
    private List<HaveItem> haveItems = new ArrayList<>();

    public void addHaveItem(HaveItem haveItem) {
        haveItems.add(haveItem);
        haveItem.setMember(this);
    }

    public static Member createMember(MemberSignupForm memberSignupForm, MemberRole role) {
        Member member = new Member();
        member.setEmail(memberSignupForm.getEmail());
        member.setName(memberSignupForm.getFirstName() + memberSignupForm.getLastName());
        member.setPassword(memberSignupForm.getPassword()); // password encoding 필요
        member.setPhone_number(memberSignupForm.getPhone_number());
        member.setMemberRole(role);

        Cart cart = new Cart();
        member.setCart(cart);
        return member;
    }

}
