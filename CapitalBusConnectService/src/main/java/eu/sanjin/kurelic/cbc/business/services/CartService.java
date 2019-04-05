package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItems;
import eu.sanjin.kurelic.cbc.repo.values.PayingMethodValues;

public interface CartService {

    void loadCartItems(CartItems items);

    boolean hasCartItem(CartItem cartItem);

    boolean addCartItem(CartItem cartItem);

    boolean updateCartItem(CartItem cartItem);

    boolean removeCartItem(CartItem cartItem);

    boolean removeAllCartItems();

    boolean saveToDatabase(PayingMethodValues payingMethod, String username);

    CartItems getCartItems();

}
