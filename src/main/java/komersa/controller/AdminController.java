package komersa.controller;

import komersa.dto.mapper.AdminDtoMapper;
import komersa.dto.request.AdminDtoRequest;
import komersa.dto.response.AdminDtoResponse;
import komersa.model.Admin;
import komersa.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static komersa.utils.TokenManager.verifyToken;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping
    @Operation(summary = "Create an admin", description = "Create new admin")
    @ApiResponse(responseCode = "201", description = "Admin saved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Invalid foreign key that is not found")
    public ResponseEntity<AdminDtoResponse> createAdmin(@RequestHeader(required = false, value = "Authorization") String token, @Valid @RequestBody AdminDtoRequest adminDtoRequest) {
        verifyToken(token);
        Admin admin = AdminDtoMapper.toModel(adminDtoRequest);
        admin = adminService.create(admin);
        return new ResponseEntity<>(AdminDtoMapper.toResponse(admin), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Admin", description = "Get Admin By Id")
    @ApiResponse(responseCode = "200", description = "Admin Get successfully")
    @ApiResponse(responseCode = "404", description = "Admin with such an Id not found")
    public ResponseEntity<AdminDtoResponse> getAdminById(@RequestHeader(required = false, value = "Authorization") String token, @PathVariable("id") Long id) {
        verifyToken(token);
        Admin admin = adminService.getById(id);
        return new ResponseEntity<>(AdminDtoMapper.toResponse(admin), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get All Admin", description = "Get All Admin")
    @ApiResponse(responseCode = "200", description = "Admin Get All successfully")
    @ApiResponse(responseCode = "404", description = "No records with Admin have been found")
    public ResponseEntity<Page<AdminDtoResponse>> getAllAdmin(@RequestHeader(required = false, value = "Authorization") String token, Pageable pageable) {
        verifyToken(token);
        Page<Admin> adminPage = adminService.getAll(pageable);
        return new ResponseEntity<>(adminPage.map(AdminDtoMapper::toResponse), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an admin", description = "Update an admin by Id and new Admin")
    @ApiResponse(responseCode = "201", description = "Admin updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Admin with such an Id not found or invalid foreign key that is not found")
    public ResponseEntity<AdminDtoResponse> updateAdmin(@RequestHeader(required = false, value = "Authorization") String token, @PathVariable("id") Long id, @Valid @RequestBody AdminDtoRequest adminDtoRequest) {
        verifyToken(token);
        Admin admin = AdminDtoMapper.toModel(adminDtoRequest);
        admin = adminService.updateById(id, admin);
        return new ResponseEntity<>(AdminDtoMapper.toResponse(admin), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an admin", description = "Delete an admin by id")
    @ApiResponse(responseCode = "204", description = "Admin deleted successfully")
    public ResponseEntity<Boolean> deleteAdmin(@RequestHeader(required = false, value = "Authorization") String token, @PathVariable("id") Long id) {
        verifyToken(token);
        return new ResponseEntity<>(adminService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}