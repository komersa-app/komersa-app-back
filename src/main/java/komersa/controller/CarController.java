package komersa.controller;

import komersa.dto.mapper.CarDtoMapper;
import komersa.dto.request.CarDtoRequest;
import komersa.dto.response.CarDtoResponse;
import komersa.model.Car;
import komersa.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import komersa.service.BrandService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
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
    public ResponseEntity<CarDtoResponse> createCar(@Valid @RequestBody CarDtoRequest carDtoRequest) {
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

    @GetMapping("/motor-types")
    @Operation(summary = "Get All motor type", description = "Get All Motor type")
    @ApiResponse(responseCode = "200", description = "Motor type Get All successfully")
    @ApiResponse(responseCode = "404", description = "No records with Motor type have been found")
    public ResponseEntity<List<String>> getAllMotorType(Pageable pageable) {
        return new ResponseEntity<>(carService.getAllMotorType(pageable), HttpStatus.OK);
    }

    @GetMapping("/types")
    @Operation(summary = "Get All type", description = "Get All type")
    @ApiResponse(responseCode = "200", description = "Type Get All successfully")
    @ApiResponse(responseCode = "404", description = "No records with Type have been found")
    public ResponseEntity<List<String>> getAllType(Pageable pageable) {
        return new ResponseEntity<>(carService.getAllType(pageable), HttpStatus.OK);
    }

    @GetMapping("/colors")
    @Operation(summary = "Get All color", description = "Get All color")
    @ApiResponse(responseCode = "200", description = "Color Get All successfully")
    @ApiResponse(responseCode = "404", description = "No records with Color have been found")
    public ResponseEntity<List<String>> getAllColor(Pageable pageable) {
        return new ResponseEntity<>(carService.getAllColor(pageable), HttpStatus.OK);
    }

    @GetMapping("/models")
    @Operation(summary = "Get All models", description = "Get All Models")
    @ApiResponse(responseCode = "200", description = "Models Get All successfully")
    @ApiResponse(responseCode = "404", description = "No records with Models have been found")
    public ResponseEntity<List<String>> getAllModels(Pageable pageable) {
        return new ResponseEntity<>(carService.getAllModels(pageable), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get All Car", description = "Get All Car")
    @ApiResponse(responseCode = "200", description = "Car Get All successfully")
    @ApiResponse(responseCode = "404", description = "No records with Car have been found")
    public ResponseEntity<Page<CarDtoResponse>> getAllCar(Pageable pageable,
        @RequestParam(required = false, value = "name") String name,
          @RequestParam(required = false, value = "description") String description,
          @RequestParam(required = false, value = "color") String color,
          @RequestParam(required = false, value = "motorType") String motorType,
          @RequestParam(required = false, value = "power") String power,
          @RequestParam(required = false, value = "status") String status,
          @RequestParam(required = false, value = "type") String type,
          @RequestParam(required = false, value = "model") String model,
          @RequestParam(required = false, value = "brand") String brand
) {
        Page<Car> carPage = carService.findByCriteria(new Car(
                name,
                description,
                color,
                motorType,
                power,
                status,
                type,
                brand,
                model
        ), pageable);
        return new ResponseEntity<>(carPage.map(CarDtoMapper::toResponse), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an car", description = "Update an car by Id and new Car")
    @ApiResponse(responseCode = "201", description = "Car updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Car with such an Id not found or invalid foreign key that is not found")
    public ResponseEntity<CarDtoResponse> updateCar(@PathVariable("id") Long id, @Valid @RequestBody CarDtoRequest carDtoRequest) {
        Car car = CarDtoMapper.toModel(carDtoRequest);
        car = carService.updateById(id, car);
        return new ResponseEntity<>(CarDtoMapper.toResponse(car), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an car", description = "Delete an car by id")
    @ApiResponse(responseCode = "204", description = "Car deleted successfully")
    public ResponseEntity<Boolean> deleteCar(@PathVariable("id") Long id) {
        return new ResponseEntity<>(carService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}