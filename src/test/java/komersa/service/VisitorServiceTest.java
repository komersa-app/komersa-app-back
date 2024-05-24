package komersa.service;


import komersa.exception.EntityNotFoundException;
import komersa.model.Visitor;
import komersa.repository.VisitorRepository;
import komersa.staticObject.StaticVisitor;
import komersa.staticObject.StaticAppointment;
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

class VisitorServiceTest {

    @Mock
    private VisitorRepository visitorRepository;
    @Mock
    private AppointmentService appointmentService;
    @InjectMocks
    private VisitorService visitorService;
    private final Visitor visitor = StaticVisitor.visitor1();
    private final Visitor visitor2 = StaticVisitor.visitor2();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        when(appointmentService.getById(StaticAppointment.ID)).thenReturn(StaticAppointment.appointment1());
	    when(visitorRepository.save(any(Visitor.class))).thenReturn(visitor);

        Visitor createdVisitor = visitorService.create(visitor);

        assertNotNull(createdVisitor);
        assertEquals(visitor, createdVisitor);
        verify(appointmentService, times(1)).getById(StaticAppointment.ID);
        verify(visitorRepository, times(1)).save(visitor);
    }

    @Test
    void testCreate_EntityNotFoundException_AppointmentNotFound() {
        when(appointmentService.getById(StaticAppointment.ID)).thenThrow(new EntityNotFoundException("Appointment not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> visitorService.create(visitor));

        assertNotNull(exception);
        assertEquals("Appointment not found", exception.getMessage());
        verify(appointmentService, times(1)).getById(StaticAppointment.ID);
        verifyNoInteractions(visitorRepository);
    }

    @Test
    void testCreate_DataAccessException() {
        when(appointmentService.getById(StaticAppointment.ID)).thenReturn(StaticAppointment.appointment1());
        when(visitorRepository.findById(StaticVisitor.ID)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> visitorService.getById(StaticVisitor.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(visitorRepository, times(1)).findById(StaticVisitor.ID);
    }

    @Test
    void testGetAll() {
        List<Visitor> visitorList = new ArrayList<>();
        visitorList.add(visitor);
        visitorList.add(visitor2);
        Page<Visitor> visitorPage = new PageImpl<>(visitorList);
        Pageable pageable = Pageable.unpaged();
        when(visitorRepository.findAll(pageable)).thenReturn(visitorPage);

        Page<Visitor> result = visitorService.getAll(pageable);

        assertEquals(visitorList.size(), result.getSize());
        assertEquals(visitor, result.getContent().get(0));
        assertEquals(visitor2, result.getContent().get(1));
    }

    @Test
    void testGetAll_AnyException() {
        when(visitorRepository.findAll(any(Pageable.class))).thenThrow(new DataAccessException("Database connection failed") {});

        Pageable pageable = Pageable.unpaged();
        RuntimeException exception = assertThrows(DataAccessException.class, () -> visitorService.getAll(pageable));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(visitorRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testUpdate_Success() {
	    Visitor existingVisitor = StaticVisitor.visitor1();
        Visitor updatedVisitor = StaticVisitor.visitor2();
        when(appointmentService.getById(StaticAppointment.ID)).thenReturn(StaticAppointment.appointment1());
	    when(visitorRepository.findById(StaticVisitor.ID)).thenReturn(java.util.Optional.of(existingVisitor));
        when(visitorRepository.save(updatedVisitor)).thenReturn(updatedVisitor);

        Visitor result = visitorService.updateById(StaticVisitor.ID, updatedVisitor);

        assertEquals(updatedVisitor, result);
        verify(visitorRepository, times(1)).findById(StaticVisitor.ID);
        verify(visitorRepository, times(1)).save(updatedVisitor);
    }

    @Test
    void testUpdateById_EntityNotFoundException_AppointmentNotFound() {
        when(visitorRepository.findById(StaticVisitor.ID)).thenReturn(java.util.Optional.of(visitor));
        when(appointmentService.getById(StaticAppointment.ID)).thenThrow(new EntityNotFoundException("Appointment not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> visitorService.updateById(StaticVisitor.ID, visitor));

        assertNotNull(exception);
        assertEquals("Appointment not found", exception.getMessage());
        verify(appointmentService, times(1)).getById(StaticAppointment.ID);
    }


    @Test
    void testUpdateById_EntityNotFoundException() {
        Visitor updatedVisitor = StaticVisitor.visitor1();
        when(visitorRepository.findById(StaticVisitor.ID)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> visitorService.updateById(StaticVisitor.ID, updatedVisitor));
        verify(visitorRepository, times(1)).findById(StaticVisitor.ID);
        verify(visitorRepository, never()).save(updatedVisitor);
    }

    @Test
    void testUpdateById_AnyException() {
        Visitor existingVisitor = StaticVisitor.visitor1();
        Visitor updatedVisitor = StaticVisitor.visitor2();
        when(visitorRepository.findById(StaticVisitor.ID)).thenReturn(java.util.Optional.of(existingVisitor));
        when(appointmentService.getById(StaticAppointment.ID)).thenReturn(StaticAppointment.appointment1());
	    when(visitorRepository.save(updatedVisitor)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> visitorService.updateById(StaticVisitor.ID, updatedVisitor));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(visitorRepository, times(1)).save(updatedVisitor);
    }

    @Test
    void testDeleteById_Success() {
        boolean result = visitorService.deleteById(StaticVisitor.ID);

        verify(visitorRepository).deleteById(StaticVisitor.ID);
        assertTrue(result);
    }

    @Test
    void testDeleteById_AnyException() {
        doThrow(new DataAccessException("Database connection failed") {}).when(visitorRepository).deleteById(StaticVisitor.ID);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> visitorService.deleteById(StaticVisitor.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(visitorRepository, times(1)).deleteById(StaticVisitor.ID);
    }
}