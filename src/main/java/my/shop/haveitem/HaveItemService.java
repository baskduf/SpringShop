package my.shop.haveitem;

import my.shop.cart.CartItem;
import my.shop.item.Item;
import my.shop.member.Member;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HaveItemService {

    public List<Item> getHaveItemsToItems(List<HaveItem> haveItems) {
        List<Item> items = new ArrayList<>();
        for (HaveItem haveItem : haveItems) {
            items.add(haveItem.getItem());
        }
        return items;
    }

    public boolean haveItem(Member member, Item item) {
        List<HaveItem> haveItems = member.getHaveItems();
        for (HaveItem haveItem : haveItems) {
            if (haveItem.getItem().getId().equals(item.getId())) {
                return true;
            }
        }
        return false;
    }

}
