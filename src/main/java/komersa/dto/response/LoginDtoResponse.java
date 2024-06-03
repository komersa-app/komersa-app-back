package komersa.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;

public record LoginDtoResponse(
        @Schema(description = "name")
        String name,
        @Schema(description = "JWT token")
        String token) {
}
