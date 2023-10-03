package my.shop.cart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.shop.auth.Auth;
import my.shop.item.Item;
import my.shop.item.ItemRepository;
import my.shop.member.Member;
import my.shop.member.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    @GetMapping("/add")
    public String cartAdd(@Auth Member member, @RequestParam(name = "itemId") Long itemId) throws Exception {
        if (member != null) {
            Item item = itemRepository.findById(itemId).orElseThrow(Exception::new);
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
            List<Item> buyItems = new ArrayList<>();
            for (CartItem cartItem : cartItems) {
                buyItems.add(cartItem.getItem());
            }
            PageRequest pageRequest = PageRequest.of(page - 1, 5);
            int start = (int) pageRequest.getOffset();
            int end = Math.min((start + pageRequest.getPageSize()), buyItems.size());

            Page<Item> itemPage = new PageImpl<>(buyItems.subList(start, end), pageRequest, buyItems.size());
            model.addAttribute("items", itemPage);
            return "web/cart";
        }
        throw new Exception();
    }

}
