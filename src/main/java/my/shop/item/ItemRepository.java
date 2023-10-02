package my.shop.item;

import my.shop.member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Item findByItemName(String itemName);

    Page<Item> findAllByOrderByIdDesc(Pageable pageable);

}
