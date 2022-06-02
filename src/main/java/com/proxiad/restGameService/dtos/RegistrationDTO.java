package com.proxiad.restGameService.dtos;

import com.proxiad.restGameService.dtos.validations.FieldMatch;

import javax.validation.constraints.NotBlank;

@FieldMatch.List({@FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match"), @FieldMatch(first = "email", second = "confirmEmail", message = "The email fields must match")})
public class RegistrationDTO {
    @NotBlank(message = "Name may not be blank!")
    private String firstName;
    @NotBlank(message = "Sirname may not be blank!")
    private String lastName;
    @NotBlank(message = "Username may not be blank!")
    private String username;
    @NotBlank(message = "Email may not be blank!")
    private String email;
    @NotBlank
    private String confirmEmail;
    @NotBlank(message = "Password may not be blank!")
    private String password;
    @NotBlank
    private String confirmPassword;

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String repeatPassword) {
        this.confirmPassword = repeatPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getConfirmEmail() {
        return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
        this.confirmEmail = confirmEmail;
    }
}