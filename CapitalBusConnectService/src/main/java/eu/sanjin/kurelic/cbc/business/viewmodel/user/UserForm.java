package eu.sanjin.kurelic.cbc.business.viewmodel.user;

import eu.sanjin.kurelic.cbc.business.utility.validator.DateOfBirth;
import eu.sanjin.kurelic.cbc.business.utility.validator.Text;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

public abstract class UserForm implements Serializable {

    @NotNull(message = "errorMessage.name.required.text")
    @Size(min = 2, message = "errorMessage.name.size.text")
    @Text(message = "errorMessage.name.invalid.text")
    private String name;

    // Can be null
    @Text(message = "errorMessage.surname.invalid.text")
    private String surname;

    @NotNull(message = "errorMessage.dateOfBirth.required.text")
    @Past(message = "errorMessage.dateOfBirth.invalid.text")
    @DateOfBirth(message = "errorMessage.dateOfBirth.invalid.text")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;

    @NotBlank(message = "errorMessage.email.required.text")
    @Email(message = "errorMessage.email.invalid.text")
    private String email;

    private Boolean newsletter = Boolean.FALSE;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(Boolean newsletter) {
        this.newsletter = newsletter;
    }

    public abstract String getIdentification();

    public abstract void setIdentification(String identification);

}
