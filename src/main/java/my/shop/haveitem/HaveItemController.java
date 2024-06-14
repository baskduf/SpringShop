package my.shop.haveitem;

import lombok.RequiredArgsConstructor;
import my.shop.auth.Auth;
import my.shop.cart.CartItem;
import my.shop.error.ErrorEnum;
import my.shop.item.Item;
import my.shop.member.Member;
import my.shop.page.PageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/have")
@RequiredArgsConstructor
public class HaveItemController {

    private final HaveItemService haveItemService;

    @GetMapping
    public String haveItemView(@Auth Member member, @RequestParam(name = "page", defaultValue = "1") Integer page, Model model) throws Exception {
        //page
        if (member != null) {

            List<HaveItem> haveItems = member.getHaveItems();
            List<Item> items = haveItemService.getHaveItemsToItems(haveItems);
            Page<Item> itemPage = PageService.makePage(items, page, 8);
            model.addAttribute("items", itemPage);
            return "web/have_item";
        }
        throw new Exception();
    }

}
