package komersa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import komersa.dto.request.UserDtoRequest;
import komersa.dto.response.UserDtoResponse;
import komersa.model.User;
import komersa.exception.EntityNotFoundException;
import komersa.service.UserService;
import komersa.staticObject.StaticUser;
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

class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final UserDtoRequest userRequest = StaticUser.userDtoRequest1();
    private final User userModel = StaticUser.user1(); 
    private final UserDtoResponse userResponse = StaticUser.userDtoResponse1();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        UserController userController = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testCreate_Success_ShouldReturnCreated() throws Exception {
        when(userService.create(any(User.class))).thenReturn(userModel);

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.usrId").value(userResponse.getUsrId()))
                .andExpect(jsonPath("$.usrFname").value(userResponse.getUsrFname()))
                .andExpect(jsonPath("$.usrEmail").value(userResponse.getUsrEmail()))
                .andExpect(jsonPath("$.usrPswd").value(userResponse.getUsrPswd()));
    }

    @Test
    void testCreate_InvalidInput_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testCreate_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(userService.create(any(User.class))).thenThrow(new EntityNotFoundException("User not found"));

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("User not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testCreate_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(userService).create(any(User.class));

        mockMvc.perform(post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetById_Success_ShouldReturnOk() throws Exception {
        when(userService.getById(StaticUser.ID)).thenReturn(userModel);

        mockMvc.perform(get("/api/user/{id}", StaticUser.ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.usrId").value(userResponse.getUsrId()))
                .andExpect(jsonPath("$.usrFname").value(userResponse.getUsrFname()))
                .andExpect(jsonPath("$.usrEmail").value(userResponse.getUsrEmail()))
                .andExpect(jsonPath("$.usrPswd").value(userResponse.getUsrPswd()));
    }

    @Test
    void testGetById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(userService.getById(any())).thenThrow(new EntityNotFoundException("User not found"));

        mockMvc.perform(get("/api/user/"+StaticUser.ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("User not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testGetById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(userService).getById(any());

        mockMvc.perform(get("/api/user/"+StaticUser.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void testGetAll_Success_ShouldReturnOk() throws Exception {
        List<User> userList = Arrays.asList(userModel, StaticUser.user1());
        Page<User> userPage = new PageImpl<>(userList);
        Pageable pageable = Pageable.unpaged();
        when(userService.getAll(pageable)).thenReturn(userPage);

        mockMvc.perform(get("/api/user/"))
                .andExpect(status().isOk())
		        .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id").value(userResponse.getId()))
                .andExpect(jsonPath("$.[1].id").value(StaticUser.userDtoResponse2().getId()));
    }

    @Test
    void testGetAll_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(userService).getAll(any(Pageable.class));

        mockMvc.perform(get("/api/user/"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }


    @Test
    void testUpdateById_Success_ShouldReturnOk() throws Exception {
        when(userService.updateById(any(), any(User.class))).thenReturn(userModel);

        mockMvc.perform(put("/api/user/"+StaticUser.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.usrId").value(userResponse.getUsrId()))
                .andExpect(jsonPath("$.usrFname").value(userResponse.getUsrFname()))
                .andExpect(jsonPath("$.usrEmail").value(userResponse.getUsrEmail()))
                .andExpect(jsonPath("$.usrPswd").value(userResponse.getUsrPswd()));
    }

    @Test
    void testUpdateById_InvalidInput_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/api/user/"+StaticUser.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void testUpdateById_EntityNotFoundException_ShouldReturnNotFound() throws Exception {
        when(userService.updateById(any(), any(User.class))).thenThrow(new EntityNotFoundException("User not found"));

        mockMvc.perform(put("/api/user/"+StaticUser.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errorMessage").value("User not found"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testUpdateById_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(userService).updateById(any(), any(User.class));

        mockMvc.perform(put("/api/user/"+StaticUser.ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testDelete_Success_ShouldReturnNoContent() throws Exception {
        when(userService.deleteById(StaticUser.ID)).thenReturn(true);

        mockMvc.perform(delete("/api/user/"+StaticUser.ID))
                .andExpect(status().isNoContent());
    }
	
    @Test
    void testDelete_AnyException_ShouldReturnBadRequest() throws Exception {
        doThrow(new DataAccessException("Database connection failed") {}).when(userService).deleteById(StaticUser.ID);

        mockMvc.perform(delete("/api/user/"+StaticUser.ID))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorMessage").value("Database connection failed"))
                .andExpect(jsonPath("$.timestamp").exists());
    }
}