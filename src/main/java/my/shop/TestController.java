package my.shop;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
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
        return "web/item_upload";
    }

    @RequestMapping("/item")
    public String testItemDetail() {
        return "web/item";
    }

    @ResponseBody
    @RequestMapping(value = "/ajaxTest.do")
    public User ajaxTest(@RequestBody User user) throws Exception {

        log.info("user={}", user);

        return user;
    }

    @Data
    static class User{
        private String id;
        private String pw;
    }

}
