package komersa.controller;

import komersa.dto.mapper.BrandDtoMapper;
import komersa.dto.request.BrandDtoRequest;
import komersa.dto.response.BrandDtoResponse;
import komersa.model.Brand;
import komersa.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brands")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @PostMapping
    @Operation(summary = "Create an brand", description = "Create new brand")
    @ApiResponse(responseCode = "201", description = "Brand saved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Invalid foreign key that is not found")
    public ResponseEntity<BrandDtoResponse> createBrand(@Valid @RequestBody BrandDtoRequest brandDtoRequest) {
        Brand brand = BrandDtoMapper.toModel(brandDtoRequest);
        brand = brandService.create(brand);
        return new ResponseEntity<>(BrandDtoMapper.toResponse(brand), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Brand", description = "Get Brand By Id")
    @ApiResponse(responseCode = "200", description = "Brand Get successfully")
    @ApiResponse(responseCode = "404", description = "Brand with such an Id not found")
    public ResponseEntity<BrandDtoResponse> getBrandById(@PathVariable("id") Long id) {
        Brand brand = brandService.getById(id);
        return new ResponseEntity<>(BrandDtoMapper.toResponse(brand), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get All Brand", description = "Get All Brand")
    @ApiResponse(responseCode = "200", description = "Brand Get All successfully")
    @ApiResponse(responseCode = "404", description = "No records with Brand have been found")
    public ResponseEntity<Page<BrandDtoResponse>> getAllBrand(Pageable pageable) {
        Page<Brand> brandPage = brandService.getAll(pageable);
        return new ResponseEntity<>(brandPage.map(BrandDtoMapper::toResponse), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an brand", description = "Update a brand by Id and new Brand")
    @ApiResponse(responseCode = "201", description = "Brand updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Brand with such an Id not found or invalid foreign key that is not found")
    public ResponseEntity<BrandDtoResponse> updateBrand(@PathVariable("id") Long id, @Valid @RequestBody BrandDtoRequest brandDtoRequest) {
        Brand brand = BrandDtoMapper.toModel(brandDtoRequest);
        brand = brandService.updateById(id, brand);
        return new ResponseEntity<>(BrandDtoMapper.toResponse(brand), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an brand", description = "Delete a brand by id")
    @ApiResponse(responseCode = "204", description = "Brand deleted successfully")
    public ResponseEntity<Boolean> deleteBrand(@PathVariable("id") Long id) {
        return new ResponseEntity<>(brandService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}