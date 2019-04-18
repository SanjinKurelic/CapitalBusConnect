package eu.sanjin.kurelic.cbc.business.exception;

import eu.sanjin.kurelic.cbc.business.viewmodel.cart.CartItem;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class InvalidCartItemException extends Exception {

    private String message;

    public InvalidCartItemException(Set<ConstraintViolation<CartItem>> violations) {
        StringBuilder message = new StringBuilder();
        for (var violation : violations) {
            message.append(violation.getMessage());
        }
        this.message = message.toString();
    }

    public InvalidCartItemException() {
    }

    @Override
    public String getMessage() {
        return message;
    }
}
