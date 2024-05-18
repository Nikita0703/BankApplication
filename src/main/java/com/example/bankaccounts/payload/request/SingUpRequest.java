package com.example.bankaccounts.payload.request;


public class SingUpRequest {

  //  @Email(message = "It should have email format")
   // @NotBlank(message = "User email is required")
    private String email;
   // @NotEmpty(message = "Please enter your name")
    private String firstname;
    //@NotEmpty(message = "Please enter your last name")
    private String lastname;
    ////@NotEmpty(message = "Please enter your username")
    private String username;
   // @NotEmpty(message = "Password is required")
    //@Size(min = 6, message = "Password must be greater than 6 letters")
    private String password;
    private String confirmPassword;



}
