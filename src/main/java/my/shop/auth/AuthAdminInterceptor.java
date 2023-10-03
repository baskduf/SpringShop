package my.shop.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.shop.member.Member;
import my.shop.member.MemberRepository;
import my.shop.member.MemberRole;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthAdminInterceptor implements HandlerInterceptor {

    private final MemberRepository memberRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String memberEmail = (String) session.getAttribute("member");
            if (memberEmail != null) {
                Member member = memberRepository.findByEmail(memberEmail);
                if (member != null) {
                    if(member.getMemberRole() == MemberRole.ADMIN)
                        return true;
                }
            }

        }
        response.sendRedirect("/");
        return false;
    }
}
