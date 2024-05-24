package komersa.service;


import komersa.exception.EntityNotFoundException;
import komersa.model.User;
import komersa.repository.UserRepository;
import komersa.staticObject.StaticUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    private final User user = StaticUser.user1();
    private final User user2 = StaticUser.user2();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
	    when(userRepository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.create(user);

        assertNotNull(createdUser);
        assertEquals(user, createdUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testCreate_DataAccessException() {
        when(userRepository.findById(StaticUser.ID)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getById(StaticUser.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(userRepository, times(1)).findById(StaticUser.ID);
    }

    @Test
    void testGetAll() {
        List<User> userList = new ArrayList<>();
        userList.add(user);
        userList.add(user2);
        Page<User> userPage = new PageImpl<>(userList);
        Pageable pageable = Pageable.unpaged();
        when(userRepository.findAll(pageable)).thenReturn(userPage);

        Page<User> result = userService.getAll(pageable);

        assertEquals(userList.size(), result.getSize());
        assertEquals(user, result.getContent().get(0));
        assertEquals(user2, result.getContent().get(1));
    }

    @Test
    void testGetAll_AnyException() {
        when(userRepository.findAll(any(Pageable.class))).thenThrow(new DataAccessException("Database connection failed") {});

        Pageable pageable = Pageable.unpaged();
        RuntimeException exception = assertThrows(DataAccessException.class, () -> userService.getAll(pageable));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(userRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testUpdate_Success() {
	    User existingUser = StaticUser.user1();
        User updatedUser = StaticUser.user2();
	    when(userRepository.findById(StaticUser.ID)).thenReturn(java.util.Optional.of(existingUser));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        User result = userService.updateById(StaticUser.ID, updatedUser);

        assertEquals(updatedUser, result);
        verify(userRepository, times(1)).findById(StaticUser.ID);
        verify(userRepository, times(1)).save(updatedUser);
    }


    @Test
    void testUpdateById_EntityNotFoundException() {
        User updatedUser = StaticUser.user1();
        when(userRepository.findById(StaticUser.ID)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.updateById(StaticUser.ID, updatedUser));
        verify(userRepository, times(1)).findById(StaticUser.ID);
        verify(userRepository, never()).save(updatedUser);
    }

    @Test
    void testUpdateById_AnyException() {
        User existingUser = StaticUser.user1();
        User updatedUser = StaticUser.user2();
        when(userRepository.findById(StaticUser.ID)).thenReturn(java.util.Optional.of(existingUser));
	    when(userRepository.save(updatedUser)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.updateById(StaticUser.ID, updatedUser));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    void testDeleteById_Success() {
        boolean result = userService.deleteById(StaticUser.ID);

        verify(userRepository).deleteById(StaticUser.ID);
        assertTrue(result);
    }

    @Test
    void testDeleteById_AnyException() {
        doThrow(new DataAccessException("Database connection failed") {}).when(userRepository).deleteById(StaticUser.ID);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.deleteById(StaticUser.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(userRepository, times(1)).deleteById(StaticUser.ID);
    }
}