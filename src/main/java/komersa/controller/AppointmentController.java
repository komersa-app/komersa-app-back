package komersa.controller;

import komersa.dto.mapper.AppointmentDtoMapper;
import komersa.dto.request.AppointmentDtoRequest;
import komersa.dto.response.AppointmentDtoResponse;
import komersa.model.Appointment;
import komersa.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentController {
    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    @Operation(summary = "Create an appointment", description = "Create new appointment")
    @ApiResponse(responseCode = "201", description = "Appointment saved successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Invalid foreign key that is not found")
    public ResponseEntity<AppointmentDtoResponse> createAppointment(@Valid @RequestBody AppointmentDtoRequest appointmentDtoRequest) {
        Appointment appointment = AppointmentDtoMapper.toModel(appointmentDtoRequest);
        appointment = appointmentService.create(appointment);
        return new ResponseEntity<>(AppointmentDtoMapper.toResponse(appointment), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Appointment", description = "Get Appointment By Id")
    @ApiResponse(responseCode = "200", description = "Appointment Get successfully")
    @ApiResponse(responseCode = "404", description = "Appointment with such an Id not found")
    public ResponseEntity<AppointmentDtoResponse> getAppointmentById(@PathVariable("id") Long id) {
        Appointment appointment = appointmentService.getById(id);
        return new ResponseEntity<>(AppointmentDtoMapper.toResponse(appointment), HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get All Appointment", description = "Get All Appointment")
    @ApiResponse(responseCode = "200", description = "Appointment Get All successfully")
    @ApiResponse(responseCode = "404", description = "No records with Appointment have been found")
    public ResponseEntity<Page<AppointmentDtoResponse>> getAllAppointment(Pageable pageable) {
        Page<Appointment> appointmentPage = appointmentService.getAll(pageable);
        return new ResponseEntity<>(appointmentPage.map(AppointmentDtoMapper::toResponse), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an appointment", description = "Update an appointment by Id and new Appointment")
    @ApiResponse(responseCode = "201", description = "Appointment updated successfully")
    @ApiResponse(responseCode = "400", description = "Invalid input")
    @ApiResponse(responseCode = "404", description = "Appointment with such an Id not found or invalid foreign key that is not found")
    public ResponseEntity<AppointmentDtoResponse> updateAppointment(@PathVariable("id") Long id, @Valid @RequestBody AppointmentDtoRequest appointmentDtoRequest) {
        Appointment appointment = AppointmentDtoMapper.toModel(appointmentDtoRequest);
        appointment = appointmentService.updateById(id, appointment);
        return new ResponseEntity<>(AppointmentDtoMapper.toResponse(appointment), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an appointment", description = "Delete an appointment by id")
    @ApiResponse(responseCode = "204", description = "Appointment deleted successfully")
    public ResponseEntity<Boolean> deleteAppointment(@PathVariable("id") Long id) {
        return new ResponseEntity<>(appointmentService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}