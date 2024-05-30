package komersa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import komersa.domain.LoginAttempt;
import komersa.dto.request.LoginDtoRequest;
import komersa.dto.response.ApiErrorResponse;
import komersa.dto.response.LoginAttemptResponse;
import komersa.dto.response.LoginDtoResponse;
import komersa.helper.JwtHelper;
import komersa.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final LoginService loginService;

    public LoginController(AuthenticationManager authenticationManager, LoginService loginService) {
        this.authenticationManager = authenticationManager;
        this.loginService = loginService;
    }

    @Operation(summary = "Authenticate user and return token")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = LoginDtoResponse.class)))
    @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @PostMapping(value = "/login")
    public ResponseEntity<LoginDtoResponse> login(@Valid @RequestBody LoginDtoRequest loginDtoRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDtoRequest.name(), loginDtoRequest.password()));
        } catch (BadCredentialsException e) {
            loginService.addLoginAttempt(loginDtoRequest.name(), false);
            throw e;
        }

        String token = JwtHelper.generateToken(loginDtoRequest.name());
        loginService.addLoginAttempt(loginDtoRequest.name(), true);
        return ResponseEntity.ok(new LoginDtoResponse(loginDtoRequest.name(), token));
    }

    @Operation(summary = "Get recent login attempts")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = LoginDtoResponse.class)))
    @ApiResponse(responseCode = "403", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))//forbidden
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @GetMapping(value = "/loginAttempts")
    public ResponseEntity<List<LoginAttemptResponse>> loginAttempts(@RequestHeader("Authorization") String token) {
        String email = JwtHelper.extractUsername(token.replace("Bearer ", ""));
        List<LoginAttempt> loginAttempts = loginService.findRecentLoginAttempts(email);
        return ResponseEntity.ok(convertToDTOs(loginAttempts));
    }

    private List<LoginAttemptResponse> convertToDTOs(List<LoginAttempt> loginAttempts) {
        return loginAttempts.stream()
                .map(LoginAttemptResponse::convertToDTO)
                .collect(Collectors.toList());
    }
}
