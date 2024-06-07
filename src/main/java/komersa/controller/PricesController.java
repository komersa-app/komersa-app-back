package komersa.controller;

import komersa.dto.mapper.PricesDtoMapper;
import komersa.dto.request.PricesDtoRequest;
import komersa.dto.response.PricesDtoResponse;
import komersa.model.Prices;
import komersa.service.PricesService;
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
@RequestMapping("/api/prices")
public class PricesController {
    private final PricesService pricesService;

    public PricesController(PricesService pricesService) {
        this.pricesService = pricesService;
    }

    @PostMapping
    @Operation(summary = "Create an prices", description = "Create new prices")
    @ApiResponse(responseCode = "201", description = "Prices saved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Invalid foreign key that is not found")
    public ResponseEntity<PricesDtoResponse> createPrices(@RequestHeader(required = false, value = "Authorization") String token, @Valid @RequestBody PricesDtoRequest pricesDtoRequest) {
        verifyToken(token);
        Prices prices = PricesDtoMapper.toModel(pricesDtoRequest);
        prices = pricesService.create(prices);
        return new ResponseEntity<>(PricesDtoMapper.toResponse(prices), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Prices", description = "Get Prices By Id")
    @ApiResponse(responseCode = "200", description = "Prices Get successfully")
    @ApiResponse(responseCode = "404", description = "Prices with such an Id not found")
    public ResponseEntity<PricesDtoResponse> getPricesById(@PathVariable("id") Long id) {
        Prices prices = pricesService.getById(id);
        return new ResponseEntity<>(PricesDtoMapper.toResponse(prices), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get All Prices", description = "Get All Prices")
    @ApiResponse(responseCode = "200", description = "Prices Get All successfully")
    @ApiResponse(responseCode = "404", description = "No records with Prices have been found")
    public ResponseEntity<Page<PricesDtoResponse>> getAllPrices(Pageable pageable) {
        Page<Prices> pricesPage = pricesService.getAll(pageable);
        return new ResponseEntity<>(pricesPage.map(PricesDtoMapper::toResponse), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an prices", description = "Update an prices by Id and new Prices")
    @ApiResponse(responseCode = "201", description = "Prices updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Prices with such an Id not found or invalid foreign key that is not found")
    public ResponseEntity<PricesDtoResponse> updatePrices(@RequestHeader(required = false, value = "Authorization") String token, @PathVariable("id") Long id, @Valid @RequestBody PricesDtoRequest pricesDtoRequest) {
        verifyToken(token);
        Prices prices = PricesDtoMapper.toModel(pricesDtoRequest);
        prices = pricesService.updateById(id, prices);
        return new ResponseEntity<>(PricesDtoMapper.toResponse(prices), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an prices", description = "Delete an prices by id")
    @ApiResponse(responseCode = "204", description = "Prices deleted successfully")
    public ResponseEntity<Boolean> deletePrices(@RequestHeader(required = false, value = "Authorization") String token, @PathVariable("id") Long id) {
        verifyToken(token);
        return new ResponseEntity<>(pricesService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}