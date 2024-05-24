package komersa.service;


import komersa.exception.EntityNotFoundException;
import komersa.model.Details;
import komersa.repository.DetailsRepository;
import komersa.staticObject.StaticDetails;
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

class DetailsServiceTest {

    @Mock
    private DetailsRepository detailsRepository;
    @Mock
    private CarService carService;
    @InjectMocks
    private DetailsService detailsService;
    private final Details details = StaticDetails.details1();
    private final Details details2 = StaticDetails.details2();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
	    when(detailsRepository.save(any(Details.class))).thenReturn(details);

        Details createdDetails = detailsService.create(details);

        assertNotNull(createdDetails);
        assertEquals(details, createdDetails);
        verify(carService, times(1)).getById(StaticCar.ID);
        verify(detailsRepository, times(1)).save(details);
    }

    @Test
    void testCreate_EntityNotFoundException_CarNotFound() {
        when(carService.getById(StaticCar.ID)).thenThrow(new EntityNotFoundException("Car not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> detailsService.create(details));

        assertNotNull(exception);
        assertEquals("Car not found", exception.getMessage());
        verify(carService, times(1)).getById(StaticCar.ID);
        verifyNoInteractions(detailsRepository);
    }

    @Test
    void testCreate_DataAccessException() {
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
        when(detailsRepository.findById(StaticDetails.ID)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> detailsService.getById(StaticDetails.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(detailsRepository, times(1)).findById(StaticDetails.ID);
    }

    @Test
    void testGetAll() {
        List<Details> detailsList = new ArrayList<>();
        detailsList.add(details);
        detailsList.add(details2);
        Page<Details> detailsPage = new PageImpl<>(detailsList);
        Pageable pageable = Pageable.unpaged();
        when(detailsRepository.findAll(pageable)).thenReturn(detailsPage);

        Page<Details> result = detailsService.getAll(pageable);

        assertEquals(detailsList.size(), result.getSize());
        assertEquals(details, result.getContent().get(0));
        assertEquals(details2, result.getContent().get(1));
    }

    @Test
    void testGetAll_AnyException() {
        when(detailsRepository.findAll(any(Pageable.class))).thenThrow(new DataAccessException("Database connection failed") {});

        Pageable pageable = Pageable.unpaged();
        RuntimeException exception = assertThrows(DataAccessException.class, () -> detailsService.getAll(pageable));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(detailsRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testUpdate_Success() {
	    Details existingDetails = StaticDetails.details1();
        Details updatedDetails = StaticDetails.details2();
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
	    when(detailsRepository.findById(StaticDetails.ID)).thenReturn(java.util.Optional.of(existingDetails));
        when(detailsRepository.save(updatedDetails)).thenReturn(updatedDetails);

        Details result = detailsService.updateById(StaticDetails.ID, updatedDetails);

        assertEquals(updatedDetails, result);
        verify(detailsRepository, times(1)).findById(StaticDetails.ID);
        verify(detailsRepository, times(1)).save(updatedDetails);
    }

    @Test
    void testUpdateById_EntityNotFoundException_CarNotFound() {
        when(detailsRepository.findById(StaticDetails.ID)).thenReturn(java.util.Optional.of(details));
        when(carService.getById(StaticCar.ID)).thenThrow(new EntityNotFoundException("Car not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> detailsService.updateById(StaticDetails.ID, details));

        assertNotNull(exception);
        assertEquals("Car not found", exception.getMessage());
        verify(carService, times(1)).getById(StaticCar.ID);
    }


    @Test
    void testUpdateById_EntityNotFoundException() {
        Details updatedDetails = StaticDetails.details1();
        when(detailsRepository.findById(StaticDetails.ID)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> detailsService.updateById(StaticDetails.ID, updatedDetails));
        verify(detailsRepository, times(1)).findById(StaticDetails.ID);
        verify(detailsRepository, never()).save(updatedDetails);
    }

    @Test
    void testUpdateById_AnyException() {
        Details existingDetails = StaticDetails.details1();
        Details updatedDetails = StaticDetails.details2();
        when(detailsRepository.findById(StaticDetails.ID)).thenReturn(java.util.Optional.of(existingDetails));
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
	    when(detailsRepository.save(updatedDetails)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> detailsService.updateById(StaticDetails.ID, updatedDetails));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(detailsRepository, times(1)).save(updatedDetails);
    }

    @Test
    void testDeleteById_Success() {
        boolean result = detailsService.deleteById(StaticDetails.ID);

        verify(detailsRepository).deleteById(StaticDetails.ID);
        assertTrue(result);
    }

    @Test
    void testDeleteById_AnyException() {
        doThrow(new DataAccessException("Database connection failed") {}).when(detailsRepository).deleteById(StaticDetails.ID);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> detailsService.deleteById(StaticDetails.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(detailsRepository, times(1)).deleteById(StaticDetails.ID);
    }
}