package komersa.service;


import komersa.exception.EntityNotFoundException;
import komersa.model.Appointment;
import komersa.repository.AppointmentRepository;
import komersa.staticObject.StaticAppointment;
import komersa.staticObject.StaticCar;
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

class AppointmentServiceTest {

    @Mock
    private AppointmentRepository appointmentRepository;
    @Mock
    private CarService carService;
    @InjectMocks
    private AppointmentService appointmentService;
    private final Appointment appointment = StaticAppointment.appointment1();
    private final Appointment appointment2 = StaticAppointment.appointment2();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
	    when(appointmentRepository.save(any(Appointment.class))).thenReturn(appointment);

        Appointment createdAppointment = appointmentService.create(appointment);

        assertNotNull(createdAppointment);
        assertEquals(appointment, createdAppointment);
        verify(carService, times(1)).getById(StaticCar.ID);
        verify(appointmentRepository, times(1)).save(appointment);
    }

    @Test
    void testCreate_EntityNotFoundException_CarNotFound() {
        when(carService.getById(StaticCar.ID)).thenThrow(new EntityNotFoundException("Car not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> appointmentService.create(appointment));

        assertNotNull(exception);
        assertEquals("Car not found", exception.getMessage());
        verify(carService, times(1)).getById(StaticCar.ID);
        verifyNoInteractions(appointmentRepository);
    }

    @Test
    void testCreate_DataAccessException() {
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
        when(appointmentRepository.findById(StaticAppointment.ID)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> appointmentService.getById(StaticAppointment.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(appointmentRepository, times(1)).findById(StaticAppointment.ID);
    }

    @Test
    void testGetAll() {
        List<Appointment> appointmentList = new ArrayList<>();
        appointmentList.add(appointment);
        appointmentList.add(appointment2);
        Page<Appointment> appointmentPage = new PageImpl<>(appointmentList);
        Pageable pageable = Pageable.unpaged();
        when(appointmentRepository.findAll(pageable)).thenReturn(appointmentPage);

        Page<Appointment> result = appointmentService.getAll(pageable);

        assertEquals(appointmentList.size(), result.getSize());
        assertEquals(appointment, result.getContent().get(0));
        assertEquals(appointment2, result.getContent().get(1));
    }

    @Test
    void testGetAll_AnyException() {
        when(appointmentRepository.findAll(any(Pageable.class))).thenThrow(new DataAccessException("Database connection failed") {});

        Pageable pageable = Pageable.unpaged();
        RuntimeException exception = assertThrows(DataAccessException.class, () -> appointmentService.getAll(pageable));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(appointmentRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testUpdate_Success() {
	    Appointment existingAppointment = StaticAppointment.appointment1();
        Appointment updatedAppointment = StaticAppointment.appointment2();
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
	    when(appointmentRepository.findById(StaticAppointment.ID)).thenReturn(java.util.Optional.of(existingAppointment));
        when(appointmentRepository.save(updatedAppointment)).thenReturn(updatedAppointment);

        Appointment result = appointmentService.updateById(StaticAppointment.ID, updatedAppointment);

        assertEquals(updatedAppointment, result);
        verify(appointmentRepository, times(1)).findById(StaticAppointment.ID);
        verify(appointmentRepository, times(1)).save(updatedAppointment);
    }

    @Test
    void testUpdateById_EntityNotFoundException_CarNotFound() {
        when(appointmentRepository.findById(StaticAppointment.ID)).thenReturn(java.util.Optional.of(appointment));
        when(carService.getById(StaticCar.ID)).thenThrow(new EntityNotFoundException("Car not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> appointmentService.updateById(StaticAppointment.ID, appointment));

        assertNotNull(exception);
        assertEquals("Car not found", exception.getMessage());
        verify(carService, times(1)).getById(StaticCar.ID);
    }


    @Test
    void testUpdateById_EntityNotFoundException() {
        Appointment updatedAppointment = StaticAppointment.appointment1();
        when(appointmentRepository.findById(StaticAppointment.ID)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> appointmentService.updateById(StaticAppointment.ID, updatedAppointment));
        verify(appointmentRepository, times(1)).findById(StaticAppointment.ID);
        verify(appointmentRepository, never()).save(updatedAppointment);
    }

    @Test
    void testUpdateById_AnyException() {
        Appointment existingAppointment = StaticAppointment.appointment1();
        Appointment updatedAppointment = StaticAppointment.appointment2();
        when(appointmentRepository.findById(StaticAppointment.ID)).thenReturn(java.util.Optional.of(existingAppointment));
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
	    when(appointmentRepository.save(updatedAppointment)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> appointmentService.updateById(StaticAppointment.ID, updatedAppointment));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(appointmentRepository, times(1)).save(updatedAppointment);
    }

    @Test
    void testDeleteById_Success() {
        boolean result = appointmentService.deleteById(StaticAppointment.ID);

        verify(appointmentRepository).deleteById(StaticAppointment.ID);
        assertTrue(result);
    }

    @Test
    void testDeleteById_AnyException() {
        doThrow(new DataAccessException("Database connection failed") {}).when(appointmentRepository).deleteById(StaticAppointment.ID);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> appointmentService.deleteById(StaticAppointment.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(appointmentRepository, times(1)).deleteById(StaticAppointment.ID);
    }
}