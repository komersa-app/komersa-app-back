package komersa.service;


import komersa.exception.EntityNotFoundException;
import komersa.model.Admin;
import komersa.repository.AdminRepository;
import komersa.staticObject.StaticAdmin;
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

class AdminServiceTest {

    @Mock
    private AdminRepository adminRepository;
    @InjectMocks
    private AdminService adminService;
    private final Admin admin = StaticAdmin.admin1();
    private final Admin admin2 = StaticAdmin.admin2();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
	    when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        Admin createdAdmin = adminService.create(admin);

        assertNotNull(createdAdmin);
        assertEquals(admin, createdAdmin);
        verify(adminRepository, times(1)).save(admin);
    }

    @Test
    void testCreate_DataAccessException() {
        when(adminRepository.findById(StaticAdmin.ID)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> adminService.getById(StaticAdmin.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(adminRepository, times(1)).findById(StaticAdmin.ID);
    }

    @Test
    void testGetAll() {
        List<Admin> adminList = new ArrayList<>();
        adminList.add(admin);
        adminList.add(admin2);
        Page<Admin> adminPage = new PageImpl<>(adminList);
        Pageable pageable = Pageable.unpaged();
        when(adminRepository.findAll(pageable)).thenReturn(adminPage);

        Page<Admin> result = adminService.getAll(pageable);

        assertEquals(adminList.size(), result.getSize());
        assertEquals(admin, result.getContent().get(0));
        assertEquals(admin2, result.getContent().get(1));
    }

    @Test
    void testGetAll_AnyException() {
        when(adminRepository.findAll(any(Pageable.class))).thenThrow(new DataAccessException("Database connection failed") {});

        Pageable pageable = Pageable.unpaged();
        RuntimeException exception = assertThrows(DataAccessException.class, () -> adminService.getAll(pageable));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(adminRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testUpdate_Success() {
	    Admin existingAdmin = StaticAdmin.admin1();
        Admin updatedAdmin = StaticAdmin.admin2();
	    when(adminRepository.findById(StaticAdmin.ID)).thenReturn(java.util.Optional.of(existingAdmin));
        when(adminRepository.save(updatedAdmin)).thenReturn(updatedAdmin);

        Admin result = adminService.updateById(StaticAdmin.ID, updatedAdmin);

        assertEquals(updatedAdmin, result);
        verify(adminRepository, times(1)).findById(StaticAdmin.ID);
        verify(adminRepository, times(1)).save(updatedAdmin);
    }


    @Test
    void testUpdateById_EntityNotFoundException() {
        Admin updatedAdmin = StaticAdmin.admin1();
        when(adminRepository.findById(StaticAdmin.ID)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> adminService.updateById(StaticAdmin.ID, updatedAdmin));
        verify(adminRepository, times(1)).findById(StaticAdmin.ID);
        verify(adminRepository, never()).save(updatedAdmin);
    }

    @Test
    void testUpdateById_AnyException() {
        Admin existingAdmin = StaticAdmin.admin1();
        Admin updatedAdmin = StaticAdmin.admin2();
        when(adminRepository.findById(StaticAdmin.ID)).thenReturn(java.util.Optional.of(existingAdmin));
	    when(adminRepository.save(updatedAdmin)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> adminService.updateById(StaticAdmin.ID, updatedAdmin));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(adminRepository, times(1)).save(updatedAdmin);
    }

    @Test
    void testDeleteById_Success() {
        boolean result = adminService.deleteById(StaticAdmin.ID);

        verify(adminRepository).deleteById(StaticAdmin.ID);
        assertTrue(result);
    }

    @Test
    void testDeleteById_AnyException() {
        doThrow(new DataAccessException("Database connection failed") {}).when(adminRepository).deleteById(StaticAdmin.ID);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> adminService.deleteById(StaticAdmin.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(adminRepository, times(1)).deleteById(StaticAdmin.ID);
    }
}