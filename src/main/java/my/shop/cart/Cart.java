package my.shop.cart;

import jakarta.persistence.*;
import lombok.Data;
import my.shop.item.Item;
import my.shop.member.Member;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "cart")
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cart_id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "cart")
    private List<CartItem> items = new ArrayList<>();

    public void clearItem() {
        items.clear();
    }

    public void addItem(Item item) {
        CartItem cartItem = CartItem.createCartItem(item);
        this.items.add(cartItem);
        cartItem.setCart(this);
    }

    public void removeItem(Item item) {
        for (CartItem cartItem : items) {
            if (cartItem.getItem().getId().equals(item.getId())) {
                items.remove(cartItem);
                return;
            }
        }
    }

}
