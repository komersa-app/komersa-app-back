package komersa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import komersa.dto.request.VisitorDtoRequest;
import komersa.dto.response.VisitorDtoResponse;
import komersa.model.Visitor;
import komersa.exception.EntityNotFoundException;
import komersa.service.VisitorService;
import komersa.staticObject.StaticVisitor;
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

class VisitorControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VisitorService visitorService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final VisitorDtoRequest visitorRequest = StaticVisitor.visitorDtoRequest1();
    private final Visitor visitorModel = StaticVisitor.visitor1(); 
    private final VisitorDtoResponse visitorResponse = StaticVisitor.visitorDtoResponse1();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        VisitorController visitorController = new VisitorController(visitorService);
        mockMvc = MockMvcBuilders.standaloneSetup(visitorController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testCreate_Success_ShouldReturnCreated() throws Exception {
        when(visitorService.create(any(Visitor.class))).thenReturn(visitorModel);

        mockMvc.perform(post("/api/visitor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(visitorRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(visitorResponse.getId()))
                .andExpect(jsonPath("$.firstname").value(visitorResponse.getFirstname()))
                .andExpect(jsonPath("$.email").value(visitorResponse.getEmail()))
                .andExpect(jsonPath("$.tel").value(visitorResponse.getTel()))
                .andExpect(jsonPath("$.message").value(visitorResponse.getMessage()))
                .andExpect(jsonPath("$.appointment.id").value(visitorResponse.getAppointment().getId()));
    }

    @Test
    void testCreate_InvalidInput_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/visitor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreate_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(visitorService.create(any(Visitor.class))).thenThrow(new EntityNotFoundException("Visitor not found"));

        mockMvc.perform(post("/api/visitor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(visitorRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Visitor not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testCreate_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(visitorService).create(any(Visitor.class));

        mockMvc.perform(post("/api/visitor")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(visitorRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetById_Success_ShouldReturnOk() throws Exception {
        when(visitorService.getById(StaticVisitor.ID)).thenReturn(visitorModel);

        mockMvc.perform(get("/api/visitor/{id}", StaticVisitor.ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(visitorResponse.getId()))
                .andExpect(jsonPath("$.firstname").value(visitorResponse.getFirstname()))
                .andExpect(jsonPath("$.email").value(visitorResponse.getEmail()))
                .andExpect(jsonPath("$.tel").value(visitorResponse.getTel()))
                .andExpect(jsonPath("$.message").value(visitorResponse.getMessage()))
                .andExpect(jsonPath("$.appointment.id").value(visitorResponse.getAppointment().getId()));
    }

    @Test
    void testGetById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(visitorService.getById(any())).thenThrow(new EntityNotFoundException("Visitor not found"));

        mockMvc.perform(get("/api/visitor/"+StaticVisitor.ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Visitor not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(visitorService).getById(any());

        mockMvc.perform(get("/api/visitor/"+StaticVisitor.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void testGetAll_Success_ShouldReturnOk() throws Exception {
        List<Visitor> visitorList = Arrays.asList(visitorModel, StaticVisitor.visitor1());
        Page<Visitor> visitorPage = new PageImpl<>(visitorList);
        Pageable pageable = Pageable.unpaged();
        when(visitorService.getAll(pageable)).thenReturn(visitorPage);

        mockMvc.perform(get("/api/visitor/"))
                .andExpect(status().isOk())
		        .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(visitorResponse.getId()))
                .andExpect(jsonPath("$.[1].id").value(StaticVisitor.visitorDtoResponse2().getId()));
    }

    @Test
    void testGetAll_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(visitorService).getAll(any(Pageable.class));

        mockMvc.perform(get("/api/visitor/"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void testUpdateById_Success_ShouldReturnOk() throws Exception {
        when(visitorService.updateById(any(), any(Visitor.class))).thenReturn(visitorModel);

        mockMvc.perform(put("/api/visitor/"+StaticVisitor.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(visitorRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(visitorResponse.getId()))
                .andExpect(jsonPath("$.firstname").value(visitorResponse.getFirstname()))
                .andExpect(jsonPath("$.email").value(visitorResponse.getEmail()))
                .andExpect(jsonPath("$.tel").value(visitorResponse.getTel()))
                .andExpect(jsonPath("$.message").value(visitorResponse.getMessage()))
                .andExpect(jsonPath("$.appointment.id").value(visitorResponse.getAppointment().getId()));
    }

    @Test
    void testUpdateById_InvalidInput_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/api/visitor/"+StaticVisitor.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(visitorService.updateById(any(), any(Visitor.class))).thenThrow(new EntityNotFoundException("Visitor not found"));

        mockMvc.perform(put("/api/visitor/"+StaticVisitor.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(visitorRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("Visitor not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testUpdateById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(visitorService).updateById(any(), any(Visitor.class));

        mockMvc.perform(put("/api/visitor/"+StaticVisitor.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(visitorRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testDelete_Success_ShouldReturnNoContent() throws Exception {
        when(visitorService.deleteById(StaticVisitor.ID)).thenReturn(true);

        mockMvc.perform(delete("/api/visitor/"+StaticVisitor.ID))
                .andExpect(status().isNoContent());
    }
	
    @Test
    void testDelete_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(visitorService).deleteById(StaticVisitor.ID);

        mockMvc.perform(delete("/api/visitor/"+StaticVisitor.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}