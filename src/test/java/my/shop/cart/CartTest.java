package my.shop.cart;

import my.shop.item.Item;
import my.shop.item.ItemUploadForm;
import my.shop.member.Member;
import my.shop.member.MemberRepository;
import my.shop.member.MemberRole;
import my.shop.member.MemberSignupForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartTest {

    @Autowired
    MemberRepository memberRepository;

    public Member registerTester() {
        MemberSignupForm memberSignupForm = new MemberSignupForm();
        memberSignupForm.setEmail("test");
        memberSignupForm.setPassword("test");
        memberSignupForm.setFirstName("a");
        memberSignupForm.setLastName("dmin");
        memberSignupForm.setPhone_number("010-xxxx-xxxx");
        Member member = Member.createMember(memberSignupForm, MemberRole.ADMIN);
        memberRepository.save(member);
        return member;
    }

    public Item addDummyItem() {
        Item item = new Item();
        item.setItemName("test");
        return item;
    }
}