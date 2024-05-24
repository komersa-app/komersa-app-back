package komersa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import komersa.dto.request.priceDtoRequest;
import komersa.dto.response.priceDtoResponse;
import komersa.model.price;
import komersa.exception.EntityNotFoundException;
import komersa.service.priceService;
import komersa.staticObject.Staticprice;
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

class priceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private priceService priceService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
    private final priceDtoRequest priceRequest = Staticprice.priceDtoRequest1();
    private final price priceModel = Staticprice.price1(); 
    private final priceDtoResponse priceResponse = Staticprice.priceDtoResponse1();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        priceController priceController = new priceController(priceService);
        mockMvc = MockMvcBuilders.standaloneSetup(priceController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testCreate_Success_ShouldReturnCreated() throws Exception {
        when(priceService.create(any(price.class))).thenReturn(priceModel);

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
        when(priceService.create(any(price.class))).thenThrow(new EntityNotFoundException("price not found"));

        mockMvc.perform(post("/api/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priceRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("price not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testCreate_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(priceService).create(any(price.class));

        mockMvc.perform(post("/api/price")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priceRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetById_Success_ShouldReturnOk() throws Exception {
        when(priceService.getById(Staticprice.ID)).thenReturn(priceModel);

        mockMvc.perform(get("/api/price/{id}", Staticprice.ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(priceResponse.getId()))
                .andExpect(jsonPath("$.changeDatetime").value(priceResponse.getChangeDatetime().format(formatter)))
                .andExpect(jsonPath("$.car.id").value(priceResponse.getCar().getId()));
    }

    @Test
    void testGetById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(priceService.getById(any())).thenThrow(new EntityNotFoundException("price not found"));

        mockMvc.perform(get("/api/price/"+Staticprice.ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("price not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(priceService).getById(any());

        mockMvc.perform(get("/api/price/"+Staticprice.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void testGetAll_Success_ShouldReturnOk() throws Exception {
        List<price> priceList = Arrays.asList(priceModel, Staticprice.price1());
        Page<price> pricePage = new PageImpl<>(priceList);
        Pageable pageable = Pageable.unpaged();
        when(priceService.getAll(pageable)).thenReturn(pricePage);

        mockMvc.perform(get("/api/price/"))
                .andExpect(status().isOk())
		        .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(priceResponse.getId()))
                .andExpect(jsonPath("$.[1].id").value(Staticprice.priceDtoResponse2().getId()));
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
        when(priceService.updateById(any(), any(price.class))).thenReturn(priceModel);

        mockMvc.perform(put("/api/price/"+Staticprice.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priceRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(priceResponse.getId()))
                .andExpect(jsonPath("$.changeDatetime").value(priceResponse.getChangeDatetime().format(formatter)))
                .andExpect(jsonPath("$.car.id").value(priceResponse.getCar().getId()));
    }

    @Test
    void testUpdateById_InvalidInput_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/api/price/"+Staticprice.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(priceService.updateById(any(), any(price.class))).thenThrow(new EntityNotFoundException("price not found"));

        mockMvc.perform(put("/api/price/"+Staticprice.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priceRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("price not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testUpdateById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(priceService).updateById(any(), any(price.class));

        mockMvc.perform(put("/api/price/"+Staticprice.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(priceRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testDelete_Success_ShouldReturnNoContent() throws Exception {
        when(priceService.deleteById(Staticprice.ID)).thenReturn(true);

        mockMvc.perform(delete("/api/price/"+Staticprice.ID))
                .andExpect(status().isNoContent());
    }
	
    @Test
    void testDelete_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(priceService).deleteById(Staticprice.ID);

        mockMvc.perform(delete("/api/price/"+Staticprice.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}