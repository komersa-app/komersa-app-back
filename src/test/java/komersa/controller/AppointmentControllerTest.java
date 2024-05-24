package komersa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import komersa.dto.request.AppointmentDtoRequest;
import komersa.dto.response.AppointmentDtoResponse;
import komersa.model.Appointment;
import komersa.exception.EntityNotFoundException;
import komersa.service.AppointmentService;
import komersa.staticObject.StaticAppointment;
import komersa.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AppointmentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private AppointmentService appointmentService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private final AppointmentDtoRequest appointmentRequest = StaticAppointment.appointmentDtoRequest1();
    private final Appointment appointmentModel = StaticAppointment.appointment1(); 
    private final AppointmentDtoResponse appointmentResponse = StaticAppointment.appointmentDtoResponse1();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        AppointmentController appointmentController = new AppointmentController(appointmentService);
        mockMvc = MockMvcBuilders.standaloneSetup(appointmentController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testCreate_Success_ShouldReturnCreated() throws Exception {
        when(appointmentService.create(any(Appointment.class))).thenReturn(appointmentModel);

        mockMvc.perform(post("/api/appointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(appointmentResponse.getId()))
                .andExpect(jsonPath("$.name").value(appointmentResponse.getName()))
                .andExpect(jsonPath("$.datetime").value(appointmentResponse.getDatetime().format(formatter)))
                .andExpect(jsonPath("$.status").value(appointmentResponse.getStatus()))
                .andExpect(jsonPath("$.car.id").value(appointmentResponse.getCar().getId()));
    }

    @Test
    void testCreate_InvalidInput_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/appointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreate_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(appointmentService.create(any(Appointment.class))).thenThrow(new EntityNotFoundException("Appointment not found"));

        mockMvc.perform(post("/api/appointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Appointment not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testCreate_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(appointmentService).create(any(Appointment.class));

        mockMvc.perform(post("/api/appointment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetById_Success_ShouldReturnOk() throws Exception {
        when(appointmentService.getById(StaticAppointment.ID)).thenReturn(appointmentModel);

        mockMvc.perform(get("/api/appointment/{id}", StaticAppointment.ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(appointmentResponse.getId()))
                .andExpect(jsonPath("$.name").value(appointmentResponse.getName()))
                .andExpect(jsonPath("$.datetime").value(appointmentResponse.getDatetime().format(formatter)))
                .andExpect(jsonPath("$.status").value(appointmentResponse.getStatus()))
                .andExpect(jsonPath("$.car.id").value(appointmentResponse.getCar().getId()));
    }

    @Test
    void testGetById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(appointmentService.getById(any())).thenThrow(new EntityNotFoundException("Appointment not found"));

        mockMvc.perform(get("/api/appointment/"+StaticAppointment.ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Appointment not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(appointmentService).getById(any());

        mockMvc.perform(get("/api/appointment/"+StaticAppointment.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void testGetAll_Success_ShouldReturnOk() throws Exception {
        List<Appointment> appointmentList = Arrays.asList(appointmentModel, StaticAppointment.appointment1());
        Page<Appointment> appointmentPage = new PageImpl<>(appointmentList);
        Pageable pageable = Pageable.unpaged();
        when(appointmentService.getAll(pageable)).thenReturn(appointmentPage);

        mockMvc.perform(get("/api/appointment/"))
                .andExpect(status().isOk())
		        .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(appointmentResponse.getId()))
                .andExpect(jsonPath("$.[1].id").value(StaticAppointment.appointmentDtoResponse2().getId()));
    }

    @Test
    void testGetAll_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(appointmentService).getAll(any(Pageable.class));

        mockMvc.perform(get("/api/appointment/"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void testUpdateById_Success_ShouldReturnOk() throws Exception {
        when(appointmentService.updateById(any(), any(Appointment.class))).thenReturn(appointmentModel);

        mockMvc.perform(put("/api/appointment/"+StaticAppointment.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(appointmentResponse.getId()))
                .andExpect(jsonPath("$.name").value(appointmentResponse.getName()))
                .andExpect(jsonPath("$.datetime").value(appointmentResponse.getDatetime().format(formatter)))
                .andExpect(jsonPath("$.status").value(appointmentResponse.getStatus()))
                .andExpect(jsonPath("$.car.id").value(appointmentResponse.getCar().getId()));
    }

    @Test
    void testUpdateById_InvalidInput_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/api/appointment/"+StaticAppointment.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(appointmentService.updateById(any(), any(Appointment.class))).thenThrow(new EntityNotFoundException("Appointment not found"));

        mockMvc.perform(put("/api/appointment/"+StaticAppointment.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Appointment not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testUpdateById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(appointmentService).updateById(any(), any(Appointment.class));

        mockMvc.perform(put("/api/appointment/"+StaticAppointment.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(appointmentRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testDelete_Success_ShouldReturnNoContent() throws Exception {
        when(appointmentService.deleteById(StaticAppointment.ID)).thenReturn(true);

        mockMvc.perform(delete("/api/appointment/"+StaticAppointment.ID))
                .andExpect(status().isNoContent());
    }
	
    @Test
    void testDelete_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(appointmentService).deleteById(StaticAppointment.ID);

        mockMvc.perform(delete("/api/appointment/"+StaticAppointment.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}