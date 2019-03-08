package eu.sanjin.kurelic.cbc.business.utility.validator;

import javax.validation.Payload;

public @interface Text {

    // Error message
    String message() default "Text contains non alphabetic characters.";

    // default groups
    Class<?>[] groups() default {};

    // default payloads
    Class<? extends Payload>[] payload() default {};

}
