package my.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {

    @RequestMapping("/carts")
    public String carts() {
        return "carts";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/signup")
    public String signup() {
        return "signup";
    }

    @RequestMapping("/test_index")
    public String testIndex() {
        return "index";
    }

}
