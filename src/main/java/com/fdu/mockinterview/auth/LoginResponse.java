package com.fdu.mockinterview.auth;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginResponse(
        @Schema(description = "username")
        String username,
        @Schema(description = "id")
        Integer id,
        @Schema(description = "JWT token")
        String token) {

}
