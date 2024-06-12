package komersa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import komersa.dto.request.CarDtoRequest;
import komersa.dto.response.CarDtoResponse;
import komersa.model.Car;
import komersa.exception.EntityNotFoundException;
import komersa.service.CarService;
import komersa.service.DetailsService;
import komersa.staticObject.StaticCar;
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

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class CarControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CarService carService;
    @Mock
    private DetailsService detailsService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final CarDtoRequest carRequest = StaticCar.carDtoRequest1();
    private final Car carModel = StaticCar.car1(); 
    private final CarDtoResponse carResponse = StaticCar.carDtoResponse1();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        CarController carController = new CarController(carService, detailsService);
        mockMvc = MockMvcBuilders.standaloneSetup(carController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testCreate_Success_ShouldReturnCreated() throws Exception {
        when(carService.create(any(Car.class))).thenReturn(carModel);

        mockMvc.perform(post("/api/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(carResponse.getId()))
                .andExpect(jsonPath("$.name").value(carResponse.getName()))
                .andExpect(jsonPath("$.description").value(carResponse.getDescription()))
                .andExpect(jsonPath("$.color").value(carResponse.getColor()))
                .andExpect(jsonPath("$.motorType").value(carResponse.getMotorType()))
                .andExpect(jsonPath("$.power").value(carResponse.getPower()))
                .andExpect(jsonPath("$.status").value(carResponse.getStatus()))
                .andExpect(jsonPath("$.type").value(carResponse.getType()))
                .andExpect(jsonPath("$.details.id").value(carResponse.getDetails().getId()))
                .andExpect(jsonPath("$.price.id").value(carResponse.getPrice().getId()));
    }

    @Test
    void testCreate_InvalidInput_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreate_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(carService.create(any(Car.class))).thenThrow(new EntityNotFoundException("Car not found"));

        mockMvc.perform(post("/api/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Car not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testCreate_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(carService).create(any(Car.class));

        mockMvc.perform(post("/api/car")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetById_Success_ShouldReturnOk() throws Exception {
        when(carService.getById(StaticCar.ID)).thenReturn(carModel);

        mockMvc.perform(get("/api/car/{id}", StaticCar.ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(carResponse.getId()))
                .andExpect(jsonPath("$.name").value(carResponse.getName()))
                .andExpect(jsonPath("$.description").value(carResponse.getDescription()))
                .andExpect(jsonPath("$.color").value(carResponse.getColor()))
                .andExpect(jsonPath("$.motorType").value(carResponse.getMotorType()))
                .andExpect(jsonPath("$.power").value(carResponse.getPower()))
                .andExpect(jsonPath("$.status").value(carResponse.getStatus()))
                .andExpect(jsonPath("$.type").value(carResponse.getType()))
                .andExpect(jsonPath("$.details.id").value(carResponse.getDetails().getId()))
                .andExpect(jsonPath("$.price.id").value(carResponse.getPrice().getId()));
    }

    @Test
    void testGetById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(carService.getById(any())).thenThrow(new EntityNotFoundException("Car not found"));

        mockMvc.perform(get("/api/car/"+StaticCar.ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Car not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(carService).getById(any());

        mockMvc.perform(get("/api/car/"+StaticCar.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void testGetAll_Success_ShouldReturnOk() throws Exception {
        List<Car> carList = Arrays.asList(carModel, StaticCar.car1());
        Page<Car> carPage = new PageImpl<>(carList);
        Pageable pageable = Pageable.unpaged();
        when(carService.getAll(pageable)).thenReturn(carPage);

        mockMvc.perform(get("/api/car/"))
                .andExpect(status().isOk())
		        .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(carResponse.getId()))
                .andExpect(jsonPath("$.[1].id").value(StaticCar.carDtoResponse2().getId()));
    }

    @Test
    void testGetAll_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(carService).getAll(any(Pageable.class));

        mockMvc.perform(get("/api/car/"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void testUpdateById_Success_ShouldReturnOk() throws Exception {
        when(carService.updateById(any(), any(Car.class))).thenReturn(carModel);

        mockMvc.perform(put("/api/car/"+StaticCar.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(carResponse.getId()))
                .andExpect(jsonPath("$.name").value(carResponse.getName()))
                .andExpect(jsonPath("$.description").value(carResponse.getDescription()))
                .andExpect(jsonPath("$.color").value(carResponse.getColor()))
                .andExpect(jsonPath("$.motorType").value(carResponse.getMotorType()))
                .andExpect(jsonPath("$.power").value(carResponse.getPower()))
                .andExpect(jsonPath("$.status").value(carResponse.getStatus()))
                .andExpect(jsonPath("$.type").value(carResponse.getType()))
                .andExpect(jsonPath("$.details.id").value(carResponse.getDetails().getId()))
                .andExpect(jsonPath("$.price.id").value(carResponse.getPrice().getId()));
    }

    @Test
    void testUpdateById_InvalidInput_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/api/car/"+StaticCar.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(carService.updateById(any(), any(Car.class))).thenThrow(new EntityNotFoundException("Car not found"));

        mockMvc.perform(put("/api/car/"+StaticCar.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Car not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testUpdateById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(carService).updateById(any(), any(Car.class));

        mockMvc.perform(put("/api/car/"+StaticCar.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testDelete_Success_ShouldReturnNoContent() throws Exception {
        when(carService.deleteById(StaticCar.ID)).thenReturn(true);

        mockMvc.perform(delete("/api/car/"+StaticCar.ID))
                .andExpect(status().isNoContent());
    }
	
    @Test
    void testDelete_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(carService).deleteById(StaticCar.ID);

        mockMvc.perform(delete("/api/car/"+StaticCar.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}