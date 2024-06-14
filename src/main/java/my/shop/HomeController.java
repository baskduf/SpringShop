package my.shop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.shop.auth.Auth;
import my.shop.cart.CartErrorEnum;
import my.shop.error.ErrorEnum;
import my.shop.item.Item;
import my.shop.item.ItemRepository;
import my.shop.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String home(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model, @RequestParam(name = "error", required = false) String errorCode) {
        PageRequest pageRequest = PageRequest.of(page - 1, 8);
        Page<Item> items = itemRepository.findAllByOrderByIdDesc(pageRequest);
        model.addAttribute("items", items);
        if (errorCode != null) {
            if(errorCode.equals(ErrorEnum.HAVE_CART_ITEM.toString()))
                model.addAttribute("error", "이미 카트에 동일한 아이템이 있습니다.");
            if(errorCode.equals(ErrorEnum.HAVE_ITEM.toString()))
                model.addAttribute("error", "이미 보유한 아이템 입니다.");
        }
        return "index";
    }

}
