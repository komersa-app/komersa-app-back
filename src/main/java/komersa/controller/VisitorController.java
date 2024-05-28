package komersa.controller;

import komersa.dto.mapper.VisitorDtoMapper;
import komersa.dto.request.VisitorDtoRequest;
import komersa.dto.response.VisitorDtoResponse;
import komersa.model.Visitor;
import komersa.service.VisitorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/visitor")
public class VisitorController {
    private final VisitorService visitorService;

    public VisitorController(VisitorService visitorService) {
        this.visitorService = visitorService;
    }

    @PostMapping
    @Operation(summary = "Create an visitor", description = "Create new visitor")
    @ApiResponse(responseCode = "201", description = "Visitor saved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Invalid foreign key that is not found")
    public ResponseEntity<VisitorDtoResponse> createVisitor(@Valid @RequestBody VisitorDtoRequest visitorDtoRequest) {
        Visitor visitor = VisitorDtoMapper.toModel(visitorDtoRequest);
        visitor = visitorService.create(visitor);
        return new ResponseEntity<>(VisitorDtoMapper.toResponse(visitor), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Visitor", description = "Get Visitor By Id")
    @ApiResponse(responseCode = "200", description = "Visitor Get successfully")
    @ApiResponse(responseCode = "404", description = "Visitor with such an Id not found")
    public ResponseEntity<VisitorDtoResponse> getVisitorById(@PathVariable("id") int id) {
        Visitor visitor = visitorService.getById(id);
        return new ResponseEntity<>(VisitorDtoMapper.toResponse(visitor), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get All Visitor", description = "Get All Visitor")
    @ApiResponse(responseCode = "200", description = "Visitor Get All successfully")
    @ApiResponse(responseCode = "404", description = "No records with Visitor have been found")
    public ResponseEntity<Page<VisitorDtoResponse>> getAllVisitor(Pageable pageable) {
        Page<Visitor> visitorPage = visitorService.getAll(pageable);
        return new ResponseEntity<>(visitorPage.map(VisitorDtoMapper::toResponse), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an visitor", description = "Update an visitor by Id and new Visitor")
    @ApiResponse(responseCode = "201", description = "Visitor updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Visitor with such an Id not found or invalid foreign key that is not found")
    public ResponseEntity<VisitorDtoResponse> updateVisitor(@PathVariable("id") int id, @Valid @RequestBody VisitorDtoRequest visitorDtoRequest) {
        Visitor visitor = VisitorDtoMapper.toModel(visitorDtoRequest);
        visitor = visitorService.updateById(id, visitor);
        return new ResponseEntity<>(VisitorDtoMapper.toResponse(visitor), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an visitor", description = "Delete an visitor by id")
    @ApiResponse(responseCode = "204", description = "Visitor deleted successfully")
    public ResponseEntity<Boolean> deleteVisitor(@PathVariable("id") int id) {
        return new ResponseEntity<>(visitorService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}