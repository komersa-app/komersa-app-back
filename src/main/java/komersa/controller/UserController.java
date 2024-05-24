package komersa.controller;

import komersa.dto.mapper.UserDtoMapper;
import komersa.dto.request.UserDtoRequest;
import komersa.dto.response.UserDtoResponse;
import komersa.model.User;
import komersa.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Create an user", description = "Create new user")
    @ApiResponse(responseCode = "201", description = "User saved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Invalid foreign key that is not found")
    public ResponseEntity<UserDtoResponse> createUser(@Valid @RequestBody UserDtoRequest userDtoRequest) {
        User user = UserDtoMapper.toModel(userDtoRequest);
        user = userService.create(user);
        return new ResponseEntity<>(UserDtoMapper.toResponse(user), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get User", description = "Get User By Id")
    @ApiResponse(responseCode = "200", description = "User Get successfully")
    @ApiResponse(responseCode = "404", description = "User with such an Id not found")
    public ResponseEntity<UserDtoResponse> getUserById(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        return new ResponseEntity<>(UserDtoMapper.toResponse(user), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get All User", description = "Get All User")
    @ApiResponse(responseCode = "200", description = "User Get All successfully")
    @ApiResponse(responseCode = "404", description = "No records with User have been found")
    public ResponseEntity<Page<UserDtoResponse>> getAllUser(Pageable pageable) {
        Page<User> userPage = userService.getAll(pageable);
        return new ResponseEntity<>(userPage.map(UserDtoMapper::toResponse), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an user", description = "Update an user by Id and new User")
    @ApiResponse(responseCode = "201", description = "User updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "User with such an Id not found or invalid foreign key that is not found")
    public ResponseEntity<UserDtoResponse> updateUser(@PathVariable("id") Long id, @Valid @RequestBody UserDtoRequest userDtoRequest) {
        User user = UserDtoMapper.toModel(userDtoRequest);
        user = userService.updateById(id, user);
        return new ResponseEntity<>(UserDtoMapper.toResponse(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an user", description = "Delete an user by id")
    @ApiResponse(responseCode = "204", description = "User deleted successfully")
    public ResponseEntity<Boolean> deleteUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}