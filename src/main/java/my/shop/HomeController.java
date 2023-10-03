package my.shop;

import lombok.RequiredArgsConstructor;
import my.shop.auth.Auth;
import my.shop.item.Item;
import my.shop.item.ItemRepository;
import my.shop.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String home(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model) {
        PageRequest pageRequest = PageRequest.of(page - 1, 8);
        Page<Item> items = itemRepository.findAllByOrderByIdDesc(pageRequest);
        model.addAttribute("items", items);
        return "index";
    }

}
