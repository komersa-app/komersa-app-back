package komersa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import komersa.dto.request.DetailsDtoRequest;
import komersa.dto.response.DetailsDtoResponse;
import komersa.model.Details;
import komersa.exception.EntityNotFoundException;
import komersa.service.DetailsService;
import komersa.staticObject.StaticDetails;
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

class DetailsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DetailsService detailsService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final DetailsDtoRequest detailsRequest = StaticDetails.detailsDtoRequest1();
    private final Details detailsModel = StaticDetails.details1(); 
    private final DetailsDtoResponse detailsResponse = StaticDetails.detailsDtoResponse1();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        DetailsController detailsController = new DetailsController(detailsService);
        mockMvc = MockMvcBuilders.standaloneSetup(detailsController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testCreate_Success_ShouldReturnCreated() throws Exception {
        when(detailsService.create(any(Details.class))).thenReturn(detailsModel);

        mockMvc.perform(post("/api/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detailsRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(detailsResponse.getId()))
                .andExpect(jsonPath("$.brand").value(detailsResponse.getBrand()))
                .andExpect(jsonPath("$.model").value(detailsResponse.getModel()));
    }

    @Test
    void testCreate_InvalidInput_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreate_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(detailsService.create(any(Details.class))).thenThrow(new EntityNotFoundException("Details not found"));

        mockMvc.perform(post("/api/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detailsRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Details not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testCreate_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(detailsService).create(any(Details.class));

        mockMvc.perform(post("/api/details")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detailsRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetById_Success_ShouldReturnOk() throws Exception {
        when(detailsService.getById(StaticDetails.ID)).thenReturn(detailsModel);

        mockMvc.perform(get("/api/details/{id}", StaticDetails.ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(detailsResponse.getId()))
                .andExpect(jsonPath("$.brand").value(detailsResponse.getBrand()))
                .andExpect(jsonPath("$.model").value(detailsResponse.getModel()));
    }

    @Test
    void testGetById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(detailsService.getById(any())).thenThrow(new EntityNotFoundException("Details not found"));

        mockMvc.perform(get("/api/details/"+StaticDetails.ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Details not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(detailsService).getById(any());

        mockMvc.perform(get("/api/details/"+StaticDetails.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void testGetAll_Success_ShouldReturnOk() throws Exception {
        List<Details> detailsList = Arrays.asList(detailsModel, StaticDetails.details1());
        Page<Details> detailsPage = new PageImpl<>(detailsList);
        Pageable pageable = Pageable.unpaged();
        when(detailsService.getAll(pageable)).thenReturn(detailsPage);

        mockMvc.perform(get("/api/details/"))
                .andExpect(status().isOk())
		        .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(detailsResponse.getId()))
                .andExpect(jsonPath("$.[1].id").value(StaticDetails.detailsDtoResponse2().getId()));
    }

    @Test
    void testGetAll_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(detailsService).getAll(any(Pageable.class));

        mockMvc.perform(get("/api/details/"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void testUpdateById_Success_ShouldReturnOk() throws Exception {
        when(detailsService.updateById(any(), any(Details.class))).thenReturn(detailsModel);

        mockMvc.perform(put("/api/details/"+StaticDetails.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detailsRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(detailsResponse.getId()))
                .andExpect(jsonPath("$.brand").value(detailsResponse.getBrand()))
                .andExpect(jsonPath("$.model").value(detailsResponse.getModel()));
    }

    @Test
    void testUpdateById_InvalidInput_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/api/details/"+StaticDetails.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(detailsService.updateById(any(), any(Details.class))).thenThrow(new EntityNotFoundException("Details not found"));

        mockMvc.perform(put("/api/details/"+StaticDetails.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detailsRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Details not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testUpdateById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(detailsService).updateById(any(), any(Details.class));

        mockMvc.perform(put("/api/details/"+StaticDetails.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(detailsRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testDelete_Success_ShouldReturnNoContent() throws Exception {
        when(detailsService.deleteById(StaticDetails.ID)).thenReturn(true);

        mockMvc.perform(delete("/api/details/"+StaticDetails.ID))
                .andExpect(status().isNoContent());
    }
	
    @Test
    void testDelete_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(detailsService).deleteById(StaticDetails.ID);

        mockMvc.perform(delete("/api/details/"+StaticDetails.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}