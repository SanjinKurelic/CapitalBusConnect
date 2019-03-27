package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItems;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class CartServiceImpl implements CartService {

    private CartItems items;

    @Override
    public void loadCartItems(CartItems items) {
        this.items = items;
    }

    @Override
    public boolean hasCartItem(CartItem cartItem) {
        return items.contains(cartItem);
    }

    @Override
    public boolean addCartItem(CartItem cartItem) {
        if(!hasCartItem(cartItem)) {
            return items.add(cartItem);
        }
        return false;
    }

    @Override
    public boolean updateCartItem(CartItem cartItem) {
        try {
            items.set(items.indexOf(cartItem), cartItem);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean removeCartItem(CartItem cartItem) {
        return items.remove(cartItem);
    }

    @Override
    public boolean removeAllCartItems() {
        items.clear();
        return true;
    }

    @Override
    public CartItems getCartItems() {
        return items;
    }
}
