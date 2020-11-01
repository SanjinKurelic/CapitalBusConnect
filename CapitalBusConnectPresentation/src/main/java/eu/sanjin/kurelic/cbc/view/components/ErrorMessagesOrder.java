package eu.sanjin.kurelic.cbc.view.components;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;

public class ErrorMessagesOrder {

  // Could be done with meta model
  private static final String OBJECT_NAME_APPENDER = "object_";
  private static final List<String> REGISTRATION_ORDER = List.of(
    "name", "surname", "dateOfBirth", "email", "identification", "newsletter"
  );
  private static final List<String> SETTINGS_ORDER = List.of(
    "name", "surname", "dateOfBirth", "email", "newsletter", "identification", OBJECT_NAME_APPENDER + "user"
  );

  private static int compareRegistrationOrder(ObjectError o1, ObjectError o2) {
    return compareOrder(REGISTRATION_ORDER, o1, o2);
  }

  private static int compareSettingsOrder(ObjectError o1, ObjectError o2) {
    return compareOrder(SETTINGS_ORDER, o1, o2);
  }

  private static int compareOrder(List<String> order, ObjectError o1, ObjectError o2) {
    String o1Name = getObjectOrFieldName(o1);
    String o2Name = getObjectOrFieldName(o2);
    int i1 = order.indexOf(o1Name);
    int i2 = order.indexOf(o2Name);
    return Integer.compare(i1, i2);
  }

  private static String getObjectOrFieldName(ObjectError objectError) {
    if (objectError instanceof FieldError) {
      return ((FieldError) objectError).getField();
    }
    return OBJECT_NAME_APPENDER + objectError.getObjectName();
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
