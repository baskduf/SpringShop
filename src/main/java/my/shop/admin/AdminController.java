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

        return "web/admin";
    }


}
