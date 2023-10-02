package my.shop;

import my.shop.auth.Auth;
import my.shop.member.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String home(@Auth Member member, Model model) {
        model.addAttribute("member", member);
        return "index";
    }
}
