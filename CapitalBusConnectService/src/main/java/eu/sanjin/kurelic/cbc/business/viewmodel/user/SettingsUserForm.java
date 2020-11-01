package eu.sanjin.kurelic.cbc.business.viewmodel.user;

import eu.sanjin.kurelic.cbc.business.utility.validator.FieldMatch;
import eu.sanjin.kurelic.cbc.business.utility.validator.Password;

import javax.validation.constraints.Pattern;

@FieldMatch(message = "errorMessage.identification.mustBeSame.text", firstField = "identification", secondField = "confirmedIdentification")
public class SettingsUserForm extends UserForm {

  // Can be null
  @Password(message = "errorMessage.identification.invalid.number.text")
  @Pattern(regexp = "|.{8,}", message = "errorMessage.identification.invalid.minSize.text")
  // @Size does not accept empty value
  private String identification;

  private String confirmedIdentification;

  @Override
  public String getIdentification() {
    return identification;
  }

  @Override
  public void setIdentification(String identification) {
    this.identification = identification;
  }

  public String getConfirmedIdentification() {
    return confirmedIdentification;
  }

  public void setConfirmedIdentification(String confirmedIdentification) {
    this.confirmedIdentification = confirmedIdentification;
  }
}
