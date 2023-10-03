package my.shop.cart;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import my.shop.item.Item;

@Entity
@Table(name = "cart_item")
@Data
@ToString
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    public static CartItem createCartItem(Item item) {
        CartItem cartItem = new CartItem();
        cartItem.setItem(item);
        return cartItem;
    }
}
