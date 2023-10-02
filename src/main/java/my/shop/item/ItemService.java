package my.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public Page<Item> findItems(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Item> result = itemRepository.findAllByOrderByIdDesc(pageRequest);
        return result;
    }

}
