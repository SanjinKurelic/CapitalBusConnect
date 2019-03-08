package eu.sanjin.kurelic.cbc.business.utility.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<Password, String> {

    private String message;
    private boolean requireNumbers;
    private boolean requireSpecialCharacters;

    @Override
    public void initialize(Password constraintAnnotation) {
        message = constraintAnnotation.message();
        requireNumbers = constraintAnnotation.requireNumbers();
        requireSpecialCharacters = constraintAnnotation.requireSpecialCharacters();
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
        if(requireNumbers){
            boolean hasNumber = false;
            for(int i = 0; i < s.length(); i++) {
                if(Character.isDigit(s.charAt(i))){
                    hasNumber = true;
                }
            }
            if(!hasNumber) {
                return false;
            }
        }
        // Simple validation - for real situations use more complex one
        if(requireSpecialCharacters) {
            boolean hasSpecialCharacter = false;
            for(int i = 0; i < s.length(); i++) {
                if(!Character.isLetterOrDigit(s.charAt(i))){
                    hasSpecialCharacter = true;
                }
            }
            //noinspection RedundantIfStatement
            if(!hasSpecialCharacter) {
                return false;
            }
        }
        return true;
    }

}
