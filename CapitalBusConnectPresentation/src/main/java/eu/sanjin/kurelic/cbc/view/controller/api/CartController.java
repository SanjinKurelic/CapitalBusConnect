package eu.sanjin.kurelic.cbc.view.controller.api;

import eu.sanjin.kurelic.cbc.business.services.CartService;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItem;
import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItems;
import eu.sanjin.kurelic.cbc.view.aspect.ReadFromSession;
import eu.sanjin.kurelic.cbc.view.aspect.SaveToSession;
import eu.sanjin.kurelic.cbc.view.components.SessionKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

@RestController
@RequestMapping("/api")
@RequestScope
public class CartController {

    private final CartService service;

    @Autowired
    public CartController(@Qualifier("cartServiceImpl") CartService service) {
        this.service = service;
    }

    @PostMapping("/cart")
    @ReadFromSession(sessionKey = SessionKey.CART_ID)
    @SaveToSession(sessionKey = SessionKey.CART_ID)
    public ResponseEntity<Void> addCartItem(@RequestBody CartItem cartItem) {
        if(service.addCartItem(cartItem)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/cart")
    @ReadFromSession(sessionKey = SessionKey.CART_ID)
    @SaveToSession(sessionKey = SessionKey.CART_ID)
    public ResponseEntity<Void> updateCartItem(@RequestBody CartItem cartItem) {
        if(service.updateCartItem(cartItem)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/cart")
    @ReadFromSession(sessionKey = SessionKey.CART_ID)
    @SaveToSession(sessionKey = SessionKey.CART_ID)
    public ResponseEntity<Void> removeCartItem(@RequestBody CartItem cartItem) {
        if(service.removeCartItem(cartItem)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/cart", produces = "application/json")
    @ReadFromSession(sessionKey = SessionKey.CART_ID)
    public CartItems getCartItem() {
        return service.getCartItems();
    }

}
