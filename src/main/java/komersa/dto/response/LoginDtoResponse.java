package komersa.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginDtoResponse(
        @Schema(description = "email")
        String email,
        @Schema(description = "JWT token")
        String token) {
}
