package eu.sanjin.kurelic.cbc.business.services;

import eu.sanjin.kurelic.cbc.business.configuration.ServiceConfiguration;
import eu.sanjin.kurelic.cbc.business.exception.InvalidCartItemException;
import eu.sanjin.kurelic.cbc.business.exception.InvalidSuppliedArgumentsException;
import eu.sanjin.kurelic.cbc.business.exception.InvalidUserException;
import eu.sanjin.kurelic.cbc.business.services.utility.TestConstant;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItems;
import eu.sanjin.kurelic.cbc.repo.configuration.RepositoryConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ServiceConfiguration.class, RepositoryConfiguration.class})
// Transactional is important!! This will prevent saving to database!!
@Transactional
class CartServiceTest {

    @Autowired
    private CartService service;

    @BeforeEach
    void setUp() {
        service.loadCartItems((CartItems) TestConstant.CART_ITEMS_VALID.clone());
    }

    @Test
    void hasCartItemWrongItemNull() {
        Assertions.assertFalse(service.hasCartItem(null));
    }

    @Test
    void hasCartItemWrongItemEmpty() {
        Assertions.assertFalse(service.hasCartItem(new CartItem()));
    }

    @Test
    void hasCartItemNoResult() {
        Assertions.assertFalse(service.hasCartItem(TestConstant.CART_ITEM_VALID));
    }

    @Test
    void hasCartItem() {
        Assertions.assertTrue(service.hasCartItem(TestConstant.CART_ITEMS_VALID.get(TestConstant.FIRST_ELEMENT)));
    }

    @Test
    void addCartItemWrongItemNull() {
        Assertions.assertThrows(
                InvalidCartItemException.class,
                () -> service.addCartItem(TestConstant.CART_ITEM_INVALID)
        );
        Assertions.assertEquals(TestConstant.CART_ITEMS_VALID, service.getCartItems());
    }

    @Test
    void addCartItemWrongItemEmpty() {
        Assertions.assertThrows(
                InvalidCartItemException.class,
                () -> service.addCartItem(TestConstant.CART_ITEM_EMPTY)
        );
        Assertions.assertEquals(TestConstant.CART_ITEMS_VALID, service.getCartItems());
    }

    @Test
    void addCartItem() {
        Assertions.assertDoesNotThrow(() -> service.addCartItem(TestConstant.CART_ITEM_VALID));
        Assertions.assertTrue(service.getCartItems().contains(TestConstant.CART_ITEM_VALID));
    }

    @Test
    void updateCartItemWrongItemNull() {
        Assertions.assertThrows(
                InvalidCartItemException.class,
                () -> service.updateCartItem(TestConstant.CART_ITEM_INVALID)
        );
        Assertions.assertEquals(TestConstant.CART_ITEMS_VALID, service.getCartItems());
    }

    @Test
    void updateCartItemWrongItemEmpty() {
        Assertions.assertThrows(
                InvalidCartItemException.class,
                () -> service.updateCartItem(TestConstant.CART_ITEM_EMPTY)
        );
        Assertions.assertEquals(TestConstant.CART_ITEMS_VALID, service.getCartItems());
    }

    @Test
    void updateCartItemWrongItem() {
        Assertions.assertThrows(
                InvalidCartItemException.class,
                () -> service.updateCartItem(TestConstant.CART_ITEM_VALID)
        );
        Assertions.assertEquals(TestConstant.CART_ITEMS_VALID, service.getCartItems());
    }

    @Test
    void updateCartItem() {
        var _item = service.getCartItems().get(TestConstant.FIRST_ELEMENT);
        _item.setNumberOfAdults(TestConstant.NUMBER_OF_PEOPLE_2);
        Assertions.assertDoesNotThrow(() -> service.updateCartItem(_item));
        Assertions.assertEquals(
                TestConstant.NUMBER_OF_PEOPLE_2,
                service.getCartItems().get(TestConstant.FIRST_ELEMENT).getNumberOfAdults()
        );
    }

    @Test
    void removeCartItemWrongItem() {
        Assertions.assertThrows(
                InvalidCartItemException.class,
                () -> service.removeCartItem(TestConstant.CART_ITEM_VALID)
        );
        Assertions.assertEquals(TestConstant.CART_ITEMS_VALID, service.getCartItems());
    }

    @Test
    void removeCartItemWrongItemEmpty() {
        Assertions.assertThrows(
                InvalidCartItemException.class,
                () -> service.removeCartItem(TestConstant.CART_ITEM_EMPTY)
        );
        Assertions.assertEquals(TestConstant.CART_ITEMS_VALID, service.getCartItems());
    }

    @Test
    void removeCartItemWrongItemNull() {
        Assertions.assertThrows(
                InvalidCartItemException.class,
                () -> service.removeCartItem(TestConstant.CART_ITEM_INVALID)
        );
        Assertions.assertEquals(TestConstant.CART_ITEMS_VALID, service.getCartItems());
    }

    @Test
    void removeCartItem() {
        var _item = service.getCartItems().get(TestConstant.FIRST_ELEMENT);
        Assertions.assertDoesNotThrow(() -> service.removeCartItem(_item));
        Assertions.assertEquals(TestConstant.CART_ITEMS_VALID.size() - 1, service.getCartItems().size());
    }

    @Test
    void removeAllCartItems() {
        service.removeAllCartItems();
        Assertions.assertTrue(service.getCartItems().isEmpty());
    }

    @Test
    void saveToDatabaseWrongStoredListNull() {
        service.removeAllCartItems();
        Assertions.assertDoesNotThrow(
                () -> service.saveToDatabase(TestConstant.PAYING_METHOD_VALID,
                        TestConstant.USERNAME_VALID)
        );
    }

    @Test
    void saveToDatabaseWrongPayment() {
        Assertions.assertThrows(
                InvalidSuppliedArgumentsException.class,
                () -> service.saveToDatabase(TestConstant.PAYING_METHOD_INVALID, TestConstant.USERNAME_VALID)
        );
    }

    @Test
    void saveToDatabaseWrongUsernameEmpty() {
        Assertions.assertThrows(
                InvalidUserException.class,
                () -> service.saveToDatabase(TestConstant.PAYING_METHOD_VALID, TestConstant.USERNAME_EMPTY)
        );
    }

    @Test
    void saveToDatabaseWrongUsernameNull() {
        Assertions.assertThrows(
                InvalidSuppliedArgumentsException.class,
                () -> service.saveToDatabase(TestConstant.PAYING_METHOD_VALID, TestConstant.USERNAME_NULL)
        );
    }

    @Test
    void saveToDatabaseWrongUsername() {
        Assertions.assertThrows(
                InvalidUserException.class,
                () -> service.saveToDatabase(TestConstant.PAYING_METHOD_VALID, TestConstant.USERNAME_INVALID)
        );
    }

    @Test
    void saveToDatabase() {
        Assertions.assertDoesNotThrow(
                () -> service.saveToDatabase(TestConstant.PAYING_METHOD_VALID,
                        TestConstant.USERNAME_VALID));
    }

}