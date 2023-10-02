package my.shop.admin;

import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.shop.item.Item;
import my.shop.item.ItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String adminView(@RequestParam(name = "page", defaultValue = "1") Integer page, Model model) {

        PageRequest pageRequest = PageRequest.of(page - 1, 5);
        Page<Item> items = itemRepository.findAllByOrderByIdDesc(pageRequest);
        model.addAttribute("items", items);

//        log.info("page of items = {}", items.getContent());
        return "web/admin";
    }

    @PostConstruct
    void dummyItems() {
        for (int i = 0; i < 40; i++) {
            Item item = new Item();
            item.setItemName("test" + i);
            item.setDescription("test description");
            item.setPrice(100);
            item.setDiscountPrice(100);
            item.setArtist("test artist");
            itemRepository.save(item);
        }
    }

}
