package my.shop.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import my.shop.member.Member;
import my.shop.member.MemberLoginForm;
import my.shop.member.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final MemberRepository memberRepository;

    @GetMapping("/login")
    public String loginView(@ModelAttribute("member") MemberLoginForm memberLoginForm) {
        return "web/login";
    }

    @PostMapping("/login")
    public String loginProcess(@Validated @ModelAttribute("member") MemberLoginForm memberLoginForm, BindingResult bindingResult, HttpServletRequest request) {
        Member findMember = memberRepository.findByEmail(memberLoginForm.getEmail());
        if (findMember == null) {
            bindingResult.reject("loginNotExistAccount");
        } else {
            if (!findMember.getPassword().equals(memberLoginForm.getPassword())) {
                bindingResult.reject("loginNotEqualsAccount");
            }
        }

        if (bindingResult.hasErrors()) {
            return "web/login";
        }

        // 인증 성공

        HttpSession session = request.getSession(true);
        session.setAttribute("member", findMember.getName());

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(@Auth Member member, HttpServletRequest request) {
        if (member != null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
                return "redirect:/";
            }
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
    }
}
