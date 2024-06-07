package komersa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import komersa.dto.request.PricesDtoRequest;
import komersa.dto.response.PricesDtoResponse;
import komersa.model.Prices;
import komersa.exception.EntityNotFoundException;
import komersa.service.PricesService;
import komersa.staticObject.StaticPrices;
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

class PricesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PricesService pricesService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private final PricesDtoRequest pricesRequest = StaticPrices.pricesDtoRequest1();
    private final Prices pricesModel = StaticPrices.prices1(); 
    private final PricesDtoResponse pricesResponse = StaticPrices.pricesDtoResponse1();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        PricesController pricesController = new PricesController(pricesService);
        mockMvc = MockMvcBuilders.standaloneSetup(pricesController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testCreate_Success_ShouldReturnCreated() throws Exception {
        when(pricesService.create(any(Prices.class))).thenReturn(pricesModel);

        mockMvc.perform(post("/api/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pricesRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(pricesResponse.getId()))
                .andExpect(jsonPath("$.amount").value(pricesResponse.getAmount()))
                .andExpect(jsonPath("$.changeDatetime").value(pricesResponse.getChangeDatetime().format(formatter)));
    }

    @Test
    void testCreate_InvalidInput_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreate_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(pricesService.create(any(Prices.class))).thenThrow(new EntityNotFoundException("Prices not found"));

        mockMvc.perform(post("/api/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pricesRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Prices not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testCreate_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(pricesService).create(any(Prices.class));

        mockMvc.perform(post("/api/prices")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pricesRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetById_Success_ShouldReturnOk() throws Exception {
        when(pricesService.getById(StaticPrices.ID)).thenReturn(pricesModel);

        mockMvc.perform(get("/api/prices/{id}", StaticPrices.ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(pricesResponse.getId()))
                .andExpect(jsonPath("$.amount").value(pricesResponse.getAmount()))
                .andExpect(jsonPath("$.changeDatetime").value(pricesResponse.getChangeDatetime().format(formatter)));
    }

    @Test
    void testGetById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(pricesService.getById(any())).thenThrow(new EntityNotFoundException("Prices not found"));

        mockMvc.perform(get("/api/prices/"+StaticPrices.ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Prices not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(pricesService).getById(any());

        mockMvc.perform(get("/api/prices/"+StaticPrices.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void testGetAll_Success_ShouldReturnOk() throws Exception {
        List<Prices> pricesList = Arrays.asList(pricesModel, StaticPrices.prices1());
        Page<Prices> pricesPage = new PageImpl<>(pricesList);
        Pageable pageable = Pageable.unpaged();
        when(pricesService.getAll(pageable)).thenReturn(pricesPage);

        mockMvc.perform(get("/api/prices/"))
                .andExpect(status().isOk())
		        .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(pricesResponse.getId()))
                .andExpect(jsonPath("$.[1].id").value(StaticPrices.pricesDtoResponse2().getId()));
    }

    @Test
    void testGetAll_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(pricesService).getAll(any(Pageable.class));

        mockMvc.perform(get("/api/prices/"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void testUpdateById_Success_ShouldReturnOk() throws Exception {
        when(pricesService.updateById(any(), any(Prices.class))).thenReturn(pricesModel);

        mockMvc.perform(put("/api/prices/"+StaticPrices.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pricesRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(pricesResponse.getId()))
                .andExpect(jsonPath("$.amount").value(pricesResponse.getAmount()))
                .andExpect(jsonPath("$.changeDatetime").value(pricesResponse.getChangeDatetime().format(formatter)));
    }

    @Test
    void testUpdateById_InvalidInput_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/api/prices/"+StaticPrices.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(pricesService.updateById(any(), any(Prices.class))).thenThrow(new EntityNotFoundException("Prices not found"));

        mockMvc.perform(put("/api/prices/"+StaticPrices.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pricesRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Prices not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testUpdateById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(pricesService).updateById(any(), any(Prices.class));

        mockMvc.perform(put("/api/prices/"+StaticPrices.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pricesRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testDelete_Success_ShouldReturnNoContent() throws Exception {
        when(pricesService.deleteById(StaticPrices.ID)).thenReturn(true);

        mockMvc.perform(delete("/api/prices/"+StaticPrices.ID))
                .andExpect(status().isNoContent());
    }
	
    @Test
    void testDelete_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(pricesService).deleteById(StaticPrices.ID);

        mockMvc.perform(delete("/api/prices/"+StaticPrices.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}