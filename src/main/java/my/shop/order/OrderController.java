package my.shop.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.shop.item.Item;
import my.shop.item.ItemRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final ItemRepository itemRepository;

    @GetMapping("/detail/{itemId}")
    public String itemDetail(@PathVariable Long itemId, Model model) throws Exception {
        Item item = itemRepository.findById(itemId).orElseThrow(Exception::new);
        model.addAttribute("item", item);
        return "web/item_detail";
    }

}
