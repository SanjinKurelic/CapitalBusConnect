package eu.sanjin.kurelic.cbc.business.viewmodel.user;

import eu.sanjin.kurelic.cbc.business.utility.validator.Password;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistrationUserForm extends UserForm {

  @NotNull(message = "errorMessage.identification.required.text")
  @Password(message = "errorMessage.identification.invalid.number.text")
  @Size(min = 8, message = "errorMessage.identification.invalid.minSize.text")
  private String identification;


  @Override
  public String getIdentification() {
    return identification;
  }

  @Override
  public void setIdentification(String identification) {
    this.identification = identification;
  }
}
