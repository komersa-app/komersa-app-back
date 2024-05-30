package komersa.controller;

import io.jsonwebtoken.lang.Assert;
import komersa.dto.mapper.DetailsDtoMapper;
import komersa.dto.request.DetailsDtoRequest;
import komersa.dto.response.DetailsDtoResponse;
import komersa.helper.JwtHelper;
import komersa.model.Admin;
import komersa.model.Details;
import komersa.service.DetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static komersa.utils.TokenExtractor.extractToken;

@RestController
@RequestMapping("/api/details")
public class DetailsController {
    private final DetailsService detailsService;

    public DetailsController(DetailsService detailsService) {
        this.detailsService = detailsService;
    }

    @PostMapping
    @Operation(summary = "Create an details", description = "Create new details")
    @ApiResponse(responseCode = "201", description = "Details saved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Invalid foreign key that is not found")
    public ResponseEntity<DetailsDtoResponse> createDetails(@RequestHeader("Authorization") String token, @Valid @RequestBody DetailsDtoRequest detailsDtoRequest) {
        Admin admin = new Admin();
        token = extractToken(token);
        admin.setName(JwtHelper.extractUsername(token));
        Assert.isTrue(JwtHelper.validateToken(token, admin));

        Details details = DetailsDtoMapper.toModel(detailsDtoRequest);
        details = detailsService.create(details);
        return new ResponseEntity<>(DetailsDtoMapper.toResponse(details), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Details", description = "Get Details By Id")
    @ApiResponse(responseCode = "200", description = "Details Get successfully")
    @ApiResponse(responseCode = "404", description = "Details with such an Id not found")
    public ResponseEntity<DetailsDtoResponse> getDetailsById(@PathVariable("id") Long id) {
        Details details = detailsService.getById(id);
        return new ResponseEntity<>(DetailsDtoMapper.toResponse(details), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get All Details", description = "Get All Details")
    @ApiResponse(responseCode = "200", description = "Details Get All successfully")
    @ApiResponse(responseCode = "404", description = "No records with Details have been found")
    public ResponseEntity<Page<DetailsDtoResponse>> getAllDetails(Pageable pageable) {
        Page<Details> detailsPage = detailsService.getAll(pageable);
        return new ResponseEntity<>(detailsPage.map(DetailsDtoMapper::toResponse), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an details", description = "Update an details by Id and new Details")
    @ApiResponse(responseCode = "201", description = "Details updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Details with such an Id not found or invalid foreign key that is not found")
    public ResponseEntity<DetailsDtoResponse> updateDetails(@RequestHeader("Authorization") String token, @PathVariable("id") Long id, @Valid @RequestBody DetailsDtoRequest detailsDtoRequest) {
        Admin admin = new Admin();
        token = extractToken(token);
        admin.setName(JwtHelper.extractUsername(token));
        Assert.isTrue(JwtHelper.validateToken(token, admin));

        Details details = DetailsDtoMapper.toModel(detailsDtoRequest);
        details = detailsService.updateById(id, details);
        return new ResponseEntity<>(DetailsDtoMapper.toResponse(details), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an details", description = "Delete an details by id")
    @ApiResponse(responseCode = "204", description = "Details deleted successfully")
    public ResponseEntity<Boolean> deleteDetails(@RequestHeader("Authorization") String token, @PathVariable("id") Long id) {
        Admin admin = new Admin();
        token = extractToken(token);
        admin.setName(JwtHelper.extractUsername(token));
        Assert.isTrue(JwtHelper.validateToken(token, admin));

        return new ResponseEntity<>(detailsService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}