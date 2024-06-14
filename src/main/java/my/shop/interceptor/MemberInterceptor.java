package my.shop.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.shop.member.Member;
import my.shop.member.MemberRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberInterceptor implements HandlerInterceptor {

    private final MemberRepository memberRepository;

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("member") != null) {
            Member member = memberRepository.findByEmail((String) session.getAttribute("member"));
            if (member != null && modelAndView != null) {
                modelAndView.addObject("loginMember", member);
            }
        }
    }
}
