package komersa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import komersa.dto.request.ImagesDtoRequest;
import komersa.dto.response.ImagesDtoResponse;
import komersa.model.Images;
import komersa.exception.EntityNotFoundException;
import komersa.service.ImagesService;
import komersa.staticObject.StaticImages;
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

class ImagesControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ImagesService imagesService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ImagesDtoRequest imagesRequest = StaticImages.imagesDtoRequest1();
    private final Images imagesModel = StaticImages.images1(); 
    private final ImagesDtoResponse imagesResponse = StaticImages.imagesDtoResponse1();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ImagesController imagesController = new ImagesController(imagesService);
        mockMvc = MockMvcBuilders.standaloneSetup(imagesController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testCreate_Success_ShouldReturnCreated() throws Exception {
        when(imagesService.create(any(Images.class))).thenReturn(imagesModel);

        mockMvc.perform(post("/api/images")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(imagesRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(imagesResponse.getId()))
                .andExpect(jsonPath("$.url").value(imagesResponse.getUrl()));
    }

    @Test
    void testCreate_InvalidInput_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/images")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreate_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(imagesService.create(any(Images.class))).thenThrow(new EntityNotFoundException("Images not found"));

        mockMvc.perform(post("/api/images")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(imagesRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Images not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testCreate_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(imagesService).create(any(Images.class));

        mockMvc.perform(post("/api/images")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(imagesRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetById_Success_ShouldReturnOk() throws Exception {
        when(imagesService.getById(StaticImages.ID)).thenReturn(imagesModel);

        mockMvc.perform(get("/api/images/{id}", StaticImages.ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(imagesResponse.getId()))
                .andExpect(jsonPath("$.url").value(imagesResponse.getUrl()));
    }

    @Test
    void testGetById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(imagesService.getById(any())).thenThrow(new EntityNotFoundException("Images not found"));

        mockMvc.perform(get("/api/images/"+StaticImages.ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Images not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(imagesService).getById(any());

        mockMvc.perform(get("/api/images/"+StaticImages.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void testGetAll_Success_ShouldReturnOk() throws Exception {
        List<Images> imagesList = Arrays.asList(imagesModel, StaticImages.images1());
        Page<Images> imagesPage = new PageImpl<>(imagesList);
        Pageable pageable = Pageable.unpaged();
        when(imagesService.getAll(pageable)).thenReturn(imagesPage);

        mockMvc.perform(get("/api/images/"))
                .andExpect(status().isOk())
		        .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(imagesResponse.getId()))
                .andExpect(jsonPath("$.[1].id").value(StaticImages.imagesDtoResponse2().getId()));
    }

    @Test
    void testGetAll_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(imagesService).getAll(any(Pageable.class));

        mockMvc.perform(get("/api/images/"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void testUpdateById_Success_ShouldReturnOk() throws Exception {
        when(imagesService.updateById(any(), any(Images.class))).thenReturn(imagesModel);

        mockMvc.perform(put("/api/images/"+StaticImages.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(imagesRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(imagesResponse.getId()))
                .andExpect(jsonPath("$.url").value(imagesResponse.getUrl()));
    }

    @Test
    void testUpdateById_InvalidInput_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/api/images/"+StaticImages.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(imagesService.updateById(any(), any(Images.class))).thenThrow(new EntityNotFoundException("Images not found"));

        mockMvc.perform(put("/api/images/"+StaticImages.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(imagesRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Images not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testUpdateById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(imagesService).updateById(any(), any(Images.class));

        mockMvc.perform(put("/api/images/"+StaticImages.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(imagesRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testDelete_Success_ShouldReturnNoContent() throws Exception {
        when(imagesService.deleteById(StaticImages.ID)).thenReturn(true);

        mockMvc.perform(delete("/api/images/"+StaticImages.ID))
                .andExpect(status().isNoContent());
    }
	
    @Test
    void testDelete_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(imagesService).deleteById(StaticImages.ID);

        mockMvc.perform(delete("/api/images/"+StaticImages.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}