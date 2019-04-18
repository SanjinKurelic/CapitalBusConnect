package eu.sanjin.kurelic.cbc.business.exception;

import eu.sanjin.kurelic.cbc.business.viewmodel.user.UserForm;

import javax.validation.ConstraintViolation;
import java.util.Objects;
import java.util.Set;

public class InvalidUserFormItemException extends Exception {

    private String message;

    public InvalidUserFormItemException(Set<ConstraintViolation<UserForm>> violations) {
        StringBuilder message = new StringBuilder();
        for (var violation : violations) {
            message.append(violation.getMessage());
        }
        this.message = message.toString();
    }

    public InvalidUserFormItemException() {
    }

    @Override
    public String getMessage() {
        if (!Objects.isNull(message)) {
            return message;
        }
        return super.getMessage();
    }
}
