package sg.edu.nus.iss.ibfb4ssfassessment.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class Login {

    @NotNull
    @Size(max = 50, message = "Max length for email is 50 characters")
    @Pattern(regexp = "^(.+)@(.+)$", message = "Invalid email format")
    private String email;

    @NotNull // implementing NotNull to ensure birthdate is available for age calc
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Birthday cannot be a current or future date")
    private Date birthDate;

    public Login() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "Login [email=" + email + ", birthDate=" + birthDate + "]";
    }

}
