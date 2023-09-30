package my.shop.member;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/signup")
    public String signupView(@ModelAttribute("member") MemberSignupForm memberSignupForm) {
        return "web/signup";
    }

    @PostMapping("/signup")
    public String signupProcess(@Validated @ModelAttribute("member") MemberSignupForm memberSignupForm, BindingResult bindingResult) {
        if (!memberSignupForm.getPassword().equals(memberSignupForm.getConfirm_password())) {
            bindingResult.reject("signupPasswordNotEquals");
        }
        // 이미 회원이 있는지 Repository 에서 검증이 필요
        if (memberRepository.findByEmail(memberSignupForm.getEmail()) != null) {
            bindingResult.reject("signupExistMember");
        }

        if (bindingResult.hasErrors()) {
            return "web/signup";
        }

        // 검증 완료
        Member member = Member.createMember(memberSignupForm, MemberRole.NORMAL);
        memberRepository.save(member);
        log.info("saved Member = {}", member);

        return "redirect:/";
    }

    @PostConstruct
    public void registerAdmin() {
        MemberSignupForm memberSignupForm = new MemberSignupForm();
        memberSignupForm.setEmail("admin");
        memberSignupForm.setPassword("admin");
        memberSignupForm.setFirstName("a");
        memberSignupForm.setLastName("dmin");
        memberSignupForm.setPhone_number("010-xxxx-xxxx");
        Member member = Member.createMember(memberSignupForm, MemberRole.ADMIN);
        memberRepository.save(member);
        log.info("saved Admin Member = {}", member);
    }
}
