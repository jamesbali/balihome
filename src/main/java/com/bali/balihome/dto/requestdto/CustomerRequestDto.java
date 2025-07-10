package com.bali.balihome.dto.requestdto;

import jakarta.validation.constraints.*;

public record CustomerRequestDto(

        @NotBlank(message = "First name is required")
        String firstName,

        @NotBlank(message = "Last name is required")
        String lastName,

        @Email(message = "Invalid email format")
        @NotBlank(message = "Email is required")
        String email,

        @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid phone number")
        String phone,

        @NotBlank(message = "Address is required")
        String address
) {
}
