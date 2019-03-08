package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItems;

public interface CartService {

    void loadCartItems(CartItems items);

    boolean addCartItem(CartItem cartItem);

    boolean updateCartItem(CartItem cartItem);

    boolean removeCartItem(CartItem cartItem);

    boolean removeAllCartItems();

    CartItems getCartItems();

}
