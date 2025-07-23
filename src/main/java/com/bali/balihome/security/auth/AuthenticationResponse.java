package com.bali.balihome.security.auth;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthenticationResponse(

        @JsonProperty("access_token")
        String accessToken,

        @JsonProperty("refresh_token")
        String refreshToken,

        @JsonProperty("token_type")
        String tokenType,

        @JsonProperty("expires_in")
        long expiresIn
) {
}
