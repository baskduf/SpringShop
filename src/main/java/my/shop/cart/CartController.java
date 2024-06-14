package my.shop.cart;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.shop.auth.Auth;
import my.shop.error.ErrorEnum;
import my.shop.haveitem.HaveItem;
import my.shop.item.Item;
import my.shop.item.ItemRepository;
import my.shop.member.Member;
import my.shop.member.MemberRepository;
import my.shop.page.PageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/add")
    public String cartAdd(@Auth Member member, @RequestParam(name = "itemId") Long itemId, RedirectAttributes redirectAttributes) throws Exception {

        if (member != null) {
            Item item = itemRepository.findById(itemId).orElseThrow(Exception::new);
            List<CartItem> items = member.getCart().getItems();
            List<HaveItem> haveItems = member.getHaveItems();
            for (HaveItem haveItem : haveItems) {
                if (haveItem.getItem().getId().equals(item.getId())) {
                    redirectAttributes.addAttribute("error", ErrorEnum.HAVE_ITEM);
                    return "redirect:/";
                }
            }
            for (CartItem cartItem : items) {
                if (cartItem.getItem().getId().equals(item.getId())) {
                    redirectAttributes.addAttribute("error", ErrorEnum.HAVE_CART_ITEM);
                    return "redirect:/";
                }
            }
            member.getCart().addItem(item);
            memberRepository.save(member);
            return "redirect:/cart";
        }
        throw new Exception();
    }

    @GetMapping("/delete")
    public String cartDelete(@Auth Member member, @RequestParam(name = "itemId") Long itemId) throws Exception {
        if (member != null) {
            Item item = itemRepository.findById(itemId).orElseThrow(Exception::new);
            member.getCart().removeItem(item);
            memberRepository.save(member);
            return "redirect:/cart";
        }
        throw new Exception();
    }

    @GetMapping
    public String cartView(@Auth Member member, Model model, @RequestParam(name = "page", defaultValue = "1") Integer page) throws Exception {
        if (member != null) {
            List<CartItem> cartItems = member.getCart().getItems();
            List<Item> buyItems = cartService.cartItemsToItems(cartItems);
            Page<Item> itemPage = PageService.makePage(buyItems, page, 5);
            model.addAttribute("totalPrice", cartService.getTotalPrice(cartItems));
            model.addAttribute("items", itemPage);
            return "web/cart";
        }
        throw new Exception();
    }

}
