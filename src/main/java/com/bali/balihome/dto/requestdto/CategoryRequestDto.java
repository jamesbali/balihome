package com.bali.balihome.dto.requestdto;


import jakarta.validation.constraints.*;

public record CategoryRequestDto(

        @NotBlank(message = "Category name is required")
        String name,

        String description

) {
}
