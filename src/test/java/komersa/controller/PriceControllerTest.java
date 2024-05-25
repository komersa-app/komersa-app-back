package komersa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import komersa.dto.request.PriceDtoRequest;
import komersa.dto.response.PriceDtoResponse;
<<<<<<< HEAD
import komersa.model.Price;
import komersa.exception.EntityNotFoundException;
import komersa.service.PriceService;
import komersa.staticObject.Staticprice;
=======
import komersa.model.price;
import komersa.exception.EntityNotFoundException;
import komersa.service.PriceService;
import komersa.staticObject.StaticPrice;
>>>>>>> 1ed4bf4 (chore: Class price* -> Price*)
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

class PriceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PriceService priceService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
<<<<<<< HEAD
    private final PriceDtoRequest priceRequest = Staticprice.priceDtoRequest1();
    private final Price priceModel = Staticprice.price1();
    private final PriceDtoResponse priceResponse = Staticprice.priceDtoResponse1();
=======
    private final PriceDtoRequest priceRequest = StaticPrice.priceDtoRequest1();
    private final price priceModel = StaticPrice.price1();
    private final PriceDtoResponse priceResponse = StaticPrice.priceDtoResponse1();
>>>>>>> 1ed4bf4 (chore: Class price* -> Price*)

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        PriceController priceController = new PriceController(priceService);
        mockMvc = MockMvcBuilders.standaloneSetup(priceController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testCreate_Success_ShouldReturnCreated() throws Exception {
        when(priceService.create(any(Price.class))).thenReturn(priceModel);

        mockMvc.perform(post("/api/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priceRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(priceResponse.getId()))
                .andExpect(jsonPath("$.changeDatetime").value(priceResponse.getChangeDatetime().format(formatter)))
                .andExpect(jsonPath("$.car.id").value(priceResponse.getCar().getId()));
    }

    @Test
    void testCreate_InvalidInput_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreate_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(priceService.create(any(Price.class))).thenThrow(new EntityNotFoundException("price not found"));

        mockMvc.perform(post("/api/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priceRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("price not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testCreate_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(priceService).create(any(Price.class));

        mockMvc.perform(post("/api/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priceRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetById_Success_ShouldReturnOk() throws Exception {
        when(priceService.getById(StaticPrice.ID)).thenReturn(priceModel);

        mockMvc.perform(get("/api/price/{id}", StaticPrice.ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(priceResponse.getId()))
                .andExpect(jsonPath("$.changeDatetime").value(priceResponse.getChangeDatetime().format(formatter)))
                .andExpect(jsonPath("$.car.id").value(priceResponse.getCar().getId()));
    }

    @Test
    void testGetById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(priceService.getById(any())).thenThrow(new EntityNotFoundException("price not found"));

        mockMvc.perform(get("/api/price/"+ StaticPrice.ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("price not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(priceService).getById(any());

        mockMvc.perform(get("/api/price/"+ StaticPrice.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void testGetAll_Success_ShouldReturnOk() throws Exception {
<<<<<<< HEAD
        List<Price> priceList = Arrays.asList(priceModel, Staticprice.price1());
        Page<Price> pricePage = new PageImpl<>(priceList);
=======
        List<price> priceList = Arrays.asList(priceModel, StaticPrice.price1());
        Page<price> pricePage = new PageImpl<>(priceList);
>>>>>>> 1ed4bf4 (chore: Class price* -> Price*)
        Pageable pageable = Pageable.unpaged();
        when(priceService.getAll(pageable)).thenReturn(pricePage);

        mockMvc.perform(get("/api/price/"))
                .andExpect(status().isOk())
		        .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(priceResponse.getId()))
                .andExpect(jsonPath("$.[1].id").value(StaticPrice.priceDtoResponse2().getId()));
    }

    @Test
    void testGetAll_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(priceService).getAll(any(Pageable.class));

        mockMvc.perform(get("/api/price/"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void testUpdateById_Success_ShouldReturnOk() throws Exception {
        when(priceService.updateById(any(), any(Price.class))).thenReturn(priceModel);

        mockMvc.perform(put("/api/price/"+ StaticPrice.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priceRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(priceResponse.getId()))
                .andExpect(jsonPath("$.changeDatetime").value(priceResponse.getChangeDatetime().format(formatter)))
                .andExpect(jsonPath("$.car.id").value(priceResponse.getCar().getId()));
    }

    @Test
    void testUpdateById_InvalidInput_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/api/price/"+ StaticPrice.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(priceService.updateById(any(), any(Price.class))).thenThrow(new EntityNotFoundException("price not found"));

        mockMvc.perform(put("/api/price/"+ StaticPrice.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priceRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("price not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testUpdateById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(priceService).updateById(any(), any(Price.class));

        mockMvc.perform(put("/api/price/"+ StaticPrice.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priceRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testDelete_Success_ShouldReturnNoContent() throws Exception {
        when(priceService.deleteById(StaticPrice.ID)).thenReturn(true);

        mockMvc.perform(delete("/api/price/"+ StaticPrice.ID))
                .andExpect(status().isNoContent());
    }
	
    @Test
    void testDelete_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(priceService).deleteById(StaticPrice.ID);

        mockMvc.perform(delete("/api/price/"+ StaticPrice.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}