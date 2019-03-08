package eu.sanjin.kurelic.cbc.business.utility.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = PasswordValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Password {

    boolean requireNumbers() default true;

    boolean requireSpecialCharacters() default false;

    // Error message
    String message() default "Password is not valid.";

    // default groups
    Class<?>[] groups() default {};

    // default playloads
    Class<? extends Payload>[] payload() default {};

}
