package komersa.controller;

import komersa.dto.mapper.priceDtoMapper;
import komersa.dto.request.priceDtoRequest;
import komersa.dto.response.priceDtoResponse;
import komersa.model.price;
import komersa.service.priceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/price")
public class priceController {
    private final priceService priceService;

    public priceController(priceService priceService) {
        this.priceService = priceService;
    }

    @PostMapping
    @Operation(summary = "Create an price", description = "Create new price")
    @ApiResponse(responseCode = "201", description = "price saved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Invalid foreign key that is not found")
    public ResponseEntity<priceDtoResponse> createprice(@Valid @RequestBody priceDtoRequest priceDtoRequest) {
        price price = priceDtoMapper.toModel(priceDtoRequest);
        price = priceService.create(price);
        return new ResponseEntity<>(priceDtoMapper.toResponse(price), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get price", description = "Get price By Id")
    @ApiResponse(responseCode = "200", description = "price Get successfully")
    @ApiResponse(responseCode = "404", description = "price with such an Id not found")
    public ResponseEntity<priceDtoResponse> getpriceById(@PathVariable("id") Long id) {
        price price = priceService.getById(id);
        return new ResponseEntity<>(priceDtoMapper.toResponse(price), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get All price", description = "Get All price")
    @ApiResponse(responseCode = "200", description = "price Get All successfully")
    @ApiResponse(responseCode = "404", description = "No records with price have been found")
    public ResponseEntity<Page<priceDtoResponse>> getAllprice(Pageable pageable) {
        Page<price> pricePage = priceService.getAll(pageable);
        return new ResponseEntity<>(pricePage.map(priceDtoMapper::toResponse), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an price", description = "Update an price by Id and new price")
    @ApiResponse(responseCode = "201", description = "price updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "price with such an Id not found or invalid foreign key that is not found")
    public ResponseEntity<priceDtoResponse> updateprice(@PathVariable("id") Long id, @Valid @RequestBody priceDtoRequest priceDtoRequest) {
        price price = priceDtoMapper.toModel(priceDtoRequest);
        price = priceService.updateById(id, price);
        return new ResponseEntity<>(priceDtoMapper.toResponse(price), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an price", description = "Delete an price by id")
    @ApiResponse(responseCode = "204", description = "price deleted successfully")
    public ResponseEntity<Boolean> deleteprice(@PathVariable("id") Long id) {
        return new ResponseEntity<>(priceService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}