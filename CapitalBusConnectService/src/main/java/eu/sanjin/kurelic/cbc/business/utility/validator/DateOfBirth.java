package eu.sanjin.kurelic.cbc.business.utility.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DateOfBirthValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface DateOfBirth {

    int oldestPerson() default 120;

    int youngestPerson() default 18;

    String message() default "Date of Birth is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
