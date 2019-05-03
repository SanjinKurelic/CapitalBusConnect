package eu.sanjin.kurelic.cbc.view.components;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ErrorMessagesOrder {

    private static final List<String> REGISTRATION_ORDER = List.of(
            "name", "surname", "dateOfBirth", "email", "identification", "newsletter"
    );
    private static final List<String> SETTINGS_ORDER = List.of(
            "name", "surname", "dateOfBirth", "email", "newsletter", "identification", "confirmedIdentification"
    );

    private static int compareRegistrationOrder(ObjectError o1, ObjectError o2) {
        int i1 = REGISTRATION_ORDER.indexOf(((FieldError) o1).getField());
        int i2 = REGISTRATION_ORDER.indexOf(((FieldError) o2).getField());
        return Integer.compare(i1, i2);
    }

    private static int compareSettingsOrder(ObjectError o1, ObjectError o2) {
        int i1 = SETTINGS_ORDER.indexOf(((FieldError) o1).getField());
        int i2 = SETTINGS_ORDER.indexOf(((FieldError) o2).getField());
        return Integer.compare(i1, i2);
    }

    public static List<ObjectError> sortErrorsInRegistrationForm(List<ObjectError> errors) {
        List<ObjectError> newErrors = new ArrayList<>(errors);
        newErrors.sort(ErrorMessagesOrder::compareRegistrationOrder);
        return newErrors;
    }

    public static List<ObjectError> sortErrorsInSettingsForm(List<ObjectError> errors) {
        List<ObjectError> newErrors = new ArrayList<>(errors);
        newErrors.sort(ErrorMessagesOrder::compareSettingsOrder);
        return newErrors;
    }
}
