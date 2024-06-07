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
import komersa.service.AdminService;
import komersa.service.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static komersa.utils.TokenManager.extractToken;

@RestController
@RequestMapping(value = "/api/auth")
public class LoginController {
    //private final AuthenticationManager authenticationManager;
    private final LoginService loginService;
    private final AdminService adminService;

    public LoginController(LoginService loginService, AdminService adminService) {
        this.loginService = loginService;
        this.adminService = adminService;
    }

    @Operation(summary = "Authenticate user and return token")
    @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = LoginDtoResponse.class)))
    @ApiResponse(responseCode = "401", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @ApiResponse(responseCode = "500", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))
    @PostMapping(value = "/login")
    public ResponseEntity<LoginDtoResponse> login(@Valid @RequestBody LoginDtoRequest loginDtoRequest) {
        try {
            //authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDtoRequest.name(), loginDtoRequest.password()));
            if (!Objects.equals(adminService.loadUserByUsername(loginDtoRequest.name()).getPassword(), loginDtoRequest.password())) {
                throw new BadCredentialsException("Bad credentials");
            }
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
        String name = JwtHelper.extractUsername(extractToken(token));
        List<LoginAttempt> loginAttempts = loginService.findRecentLoginAttempts(name);
        return ResponseEntity.ok(convertToDTOs(loginAttempts));
    }

    private List<LoginAttemptResponse> convertToDTOs(List<LoginAttempt> loginAttempts) {
        return loginAttempts.stream()
                .map(LoginAttemptResponse::convertToDTO)
                .collect(Collectors.toList());
    }
}
