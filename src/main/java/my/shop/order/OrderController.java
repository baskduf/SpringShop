package my.shop.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.shop.auth.Auth;
import my.shop.cart.CartItem;
import my.shop.cart.CartService;
import my.shop.haveitem.HaveItem;
import my.shop.item.Item;
import my.shop.item.ItemRepository;
import my.shop.member.Member;
import my.shop.member.MemberRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final ItemRepository itemRepository;

    private final MemberRepository memberRepository;

    private final CartService cartService;

    @GetMapping("/detail/{itemId}")
    public String itemDetail(@PathVariable Long itemId, Model model) throws Exception {
        Item item = itemRepository.findById(itemId).orElseThrow(Exception::new);
        model.addAttribute("item", item);
        return "web/item_detail";
    }

    @GetMapping
    public String order(@Auth Member member) throws Exception {
        if (member != null) {
            List<CartItem> cartItems = member.getCart().getItems();
            List<Item> items = cartService.cartItemsToItems(cartItems);
            for (Item item : items) {
                HaveItem haveItem = HaveItem.createHaveItem(item);
                member.addHaveItem(haveItem);
            }
            member.getCart().clearItem();
            memberRepository.save(member);
            return "redirect:/have";
        }
        throw new Exception();
    }
}
