package com.bali.balihome.security.auth;


import jakarta.validation.constraints.NotBlank;

public record AuthenticationRequest(

        @NotBlank(message = "Username or email is required")
        String identifier, // Can be username or email

        @NotBlank(message = "Password is required")
        String password

) {
}
