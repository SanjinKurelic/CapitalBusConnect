package eu.sanjin.kurelic.cbc.business.utility.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TextValidator implements ConstraintValidator<Text, String> {

    private String message;

    @Override
    public void initialize(Text constraintAnnotation) {
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        // Set custom message
        constraintValidatorContext.disableDefaultConstraintViolation();
        constraintValidatorContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        // Validation
        // Use other validations for null
        if(s == null) {
            return true;
        }
        for(int i = 0; i < s.length(); i++){
            if(!Character.isLetterOrDigit(s.charAt(i))){
                return false;
            }
        }
        return false;
    }
}
