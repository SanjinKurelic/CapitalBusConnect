package eu.sanjin.kurelic.cbc.business.utility.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = TextValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Text {

    // Error message
    String message() default "Text contains non alphabetic characters.";

    // default groups
    Class<?>[] groups() default {};

    // default payloads
    Class<? extends Payload>[] payload() default {};

}
