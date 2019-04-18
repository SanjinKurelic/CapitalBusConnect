package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.exception.InvalidCartItemException;
import eu.sanjin.kurelic.cbc.business.exception.InvalidSuppliedArgumentsException;
import eu.sanjin.kurelic.cbc.business.exception.InvalidUserException;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItems;
import eu.sanjin.kurelic.cbc.repo.values.PayingMethodValues;

public interface CartService {

    void loadCartItems(CartItems items);

    boolean hasCartItem(CartItem cartItem);

    void addCartItem(CartItem cartItem) throws InvalidCartItemException;

    void updateCartItem(CartItem cartItem) throws InvalidCartItemException;

    void removeCartItem(CartItem cartItem) throws InvalidCartItemException;

    void removeAllCartItems();

    void saveToDatabase(PayingMethodValues payingMethod, String username) throws InvalidSuppliedArgumentsException,
            InvalidUserException;

    CartItems getCartItems();

}
