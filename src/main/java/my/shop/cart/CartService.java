package my.shop.cart;

import lombok.RequiredArgsConstructor;
import my.shop.item.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    public List<Item> cartItemsToItems(List<CartItem> cartItems) {
        List<Item> buyItems = new ArrayList<>();
        for (CartItem cartItem : cartItems) {
            buyItems.add(cartItem.getItem());
        }
        return buyItems;
    }

    public int getTotalPrice(List<CartItem> cartItems) {
        int totalPrice = 0;
        for (CartItem cartItem : cartItems) {
            totalPrice += cartItem.getItem().getPrice();
        }
        return totalPrice;
    }

}
