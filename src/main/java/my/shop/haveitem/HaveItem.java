package my.shop.haveitem;

import jakarta.persistence.*;
import lombok.Data;
import my.shop.item.Item;
import my.shop.member.Member;

@Entity
@Table(name = "have_item")
@Data
public class HaveItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "have_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    public static HaveItem createHaveItem(Item item) {
        HaveItem haveItem = new HaveItem();
        haveItem.setItem(item);
        return haveItem;
    }

}
