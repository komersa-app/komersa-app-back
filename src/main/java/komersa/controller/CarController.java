package komersa.controller;

import io.jsonwebtoken.lang.Assert;
import komersa.dto.mapper.CarDtoMapper;
import komersa.dto.request.CarDtoRequest;
import komersa.dto.response.CarDtoResponse;
import komersa.helper.JwtHelper;
import komersa.model.Admin;
import komersa.model.Car;
import komersa.service.CarService;
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
@RequestMapping("/api/car")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping
    @Operation(summary = "Create an car", description = "Create new car")
    @ApiResponse(responseCode = "201", description = "Car saved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Invalid foreign key that is not found")
    public ResponseEntity<CarDtoResponse> createCar(@RequestHeader("Authorization") String token, @Valid @RequestBody CarDtoRequest carDtoRequest) {
        Admin admin = new Admin();
        token = extractToken(token);
        admin.setName(JwtHelper.extractUsername(token));
        Assert.isTrue(JwtHelper.validateToken(token, admin));

        Car car = CarDtoMapper.toModel(carDtoRequest);
        car = carService.create(car);
        return new ResponseEntity<>(CarDtoMapper.toResponse(car), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Car", description = "Get Car By Id")
    @ApiResponse(responseCode = "200", description = "Car Get successfully")
    @ApiResponse(responseCode = "404", description = "Car with such an Id not found")
    public ResponseEntity<CarDtoResponse> getCarById(@PathVariable("id") Long id) {
        Car car = carService.getById(id);
        return new ResponseEntity<>(CarDtoMapper.toResponse(car), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get All Car", description = "Get All Car")
    @ApiResponse(responseCode = "200", description = "Car Get All successfully")
    @ApiResponse(responseCode = "404", description = "No records with Car have been found")
    public ResponseEntity<Page<CarDtoResponse>> getAllCar(Pageable pageable) {
        Page<Car> carPage = carService.getAll(pageable);
        return new ResponseEntity<>(carPage.map(CarDtoMapper::toResponse), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an car", description = "Update an car by Id and new Car")
    @ApiResponse(responseCode = "201", description = "Car updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Car with such an Id not found or invalid foreign key that is not found")
    public ResponseEntity<CarDtoResponse> updateCar(@RequestHeader("Authorization") String token, @PathVariable("id") Long id, @Valid @RequestBody CarDtoRequest carDtoRequest) {
        Admin admin = new Admin();
        token = extractToken(token);
        admin.setName(JwtHelper.extractUsername(token));
        Assert.isTrue(JwtHelper.validateToken(token, admin));

        Car car = CarDtoMapper.toModel(carDtoRequest);
        car = carService.updateById(id, car);
        return new ResponseEntity<>(CarDtoMapper.toResponse(car), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an car", description = "Delete an car by id")
    @ApiResponse(responseCode = "204", description = "Car deleted successfully")
    public ResponseEntity<Boolean> deleteCar(@RequestHeader("Authorization") String token, @PathVariable("id") Long id) {
        Admin admin = new Admin();
        token = extractToken(token);
        admin.setName(JwtHelper.extractUsername(token));
        Assert.isTrue(JwtHelper.validateToken(token, admin));

        return new ResponseEntity<>(carService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}