package eu.sanjin.kurelic.cbc.business.utility.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DateOfBirthValidator implements ConstraintValidator<DateOfBirth, LocalDate> {

  private String message;
  private int oldestPerson;
  private int youngestPerson;

  @Override
  public void initialize(DateOfBirth constraintAnnotation) {
    message = constraintAnnotation.message();
    oldestPerson = constraintAnnotation.oldestPerson();
    youngestPerson = constraintAnnotation.youngestPerson();
  }

  @Override
  public boolean isValid(LocalDate date, ConstraintValidatorContext constraintValidatorContext) {
    // Set custom message
    constraintValidatorContext.disableDefaultConstraintViolation();
    constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    // Validation
    // Use other validations for null
    if (Objects.isNull(date)) {
      return true;
    }
    long yearsOld = ChronoUnit.YEARS.between(date, LocalDate.now());
    return (yearsOld >= youngestPerson || yearsOld <= oldestPerson);
  }

}
