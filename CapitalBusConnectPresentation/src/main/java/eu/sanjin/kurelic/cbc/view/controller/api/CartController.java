package eu.sanjin.kurelic.cbc.view.controller.api;

import eu.sanjin.kurelic.cbc.business.exception.InvalidCartItemException;
import eu.sanjin.kurelic.cbc.business.services.CartService;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItems;
import eu.sanjin.kurelic.cbc.view.aspect.ReadFromSession;
import eu.sanjin.kurelic.cbc.view.aspect.SaveToSession;
import eu.sanjin.kurelic.cbc.view.components.SessionKey;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestMapping(CartController.CART_API_ROOT)
@RequestScope
public class CartController {

  private final CartService service;
  // Url
  static final String CART_API_ROOT = "/api";
  private static final String CART_URL = "/cart";

  public CartController(CartService service) {
    this.service = service;
  }

  @PostMapping(value = CART_URL, produces = MediaType.APPLICATION_JSON_VALUE)
  @ReadFromSession(SessionKey.CART_ID)
  @SaveToSession(SessionKey.CART_ID)
  public ResponseEntity<Void> addCartItem(@RequestBody CartItem cartItem) {
    try {
      service.addCartItem(cartItem);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (InvalidCartItemException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PutMapping(value = CART_URL, produces = MediaType.APPLICATION_JSON_VALUE)
  @ReadFromSession(SessionKey.CART_ID)
  @SaveToSession(SessionKey.CART_ID)
  public ResponseEntity<Void> updateCartItem(@RequestBody CartItem cartItem) {
    try {
      service.updateCartItem(cartItem);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (InvalidCartItemException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @DeleteMapping(value = CART_URL, produces = MediaType.APPLICATION_JSON_VALUE)
  @ReadFromSession(SessionKey.CART_ID)
  @SaveToSession(SessionKey.CART_ID)
  public ResponseEntity<Void> removeCartItem(@RequestBody CartItem cartItem) {
    try {
      service.removeCartItem(cartItem);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (InvalidCartItemException e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(value = CART_URL, produces = MediaType.APPLICATION_JSON_VALUE)
  @ReadFromSession(SessionKey.CART_ID)
  public CartItems getCartItem() {
    return service.getCartItems();
  }

}
