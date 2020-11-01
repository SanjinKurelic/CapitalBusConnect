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

  String message() default "Text contains non alphabetic characters.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};

}
