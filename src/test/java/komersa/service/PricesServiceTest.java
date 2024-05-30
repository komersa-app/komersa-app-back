package komersa.service;


import komersa.exception.EntityNotFoundException;
import komersa.model.Prices;
import komersa.repository.PricesRepository;
import komersa.staticObject.StaticPrices;
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

class PricesServiceTest {

    @Mock
    private PricesRepository pricesRepository;
    @Mock
    private CarService carService;
    @InjectMocks
    private PricesService pricesService;
    private final Prices prices = StaticPrices.prices1();
    private final Prices prices2 = StaticPrices.prices2();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
	    when(pricesRepository.save(any(Prices.class))).thenReturn(prices);

        Prices createdPrices = pricesService.create(prices);

        assertNotNull(createdPrices);
        assertEquals(prices, createdPrices);
        verify(carService, times(1)).getById(StaticCar.ID);
        verify(pricesRepository, times(1)).save(prices);
    }

    @Test
    void testCreate_EntityNotFoundException_CarNotFound() {
        when(carService.getById(StaticCar.ID)).thenThrow(new EntityNotFoundException("Car not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> pricesService.create(prices));

        assertNotNull(exception);
        assertEquals("Car not found", exception.getMessage());
        verify(carService, times(1)).getById(StaticCar.ID);
        verifyNoInteractions(pricesRepository);
    }

    @Test
    void testCreate_DataAccessException() {
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
        when(pricesRepository.findById(StaticPrices.ID)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> pricesService.getById(StaticPrices.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(pricesRepository, times(1)).findById(StaticPrices.ID);
    }

    @Test
    void testGetAll() {
        List<Prices> pricesList = new ArrayList<>();
        pricesList.add(prices);
        pricesList.add(prices2);
        Page<Prices> pricesPage = new PageImpl<>(pricesList);
        Pageable pageable = Pageable.unpaged();
        when(pricesRepository.findAll(pageable)).thenReturn(pricesPage);

        Page<Prices> result = pricesService.getAll(pageable);

        assertEquals(pricesList.size(), result.getSize());
        assertEquals(prices, result.getContent().get(0));
        assertEquals(prices2, result.getContent().get(1));
    }

    @Test
    void testGetAll_AnyException() {
        when(pricesRepository.findAll(any(Pageable.class))).thenThrow(new DataAccessException("Database connection failed") {});

        Pageable pageable = Pageable.unpaged();
        RuntimeException exception = assertThrows(DataAccessException.class, () -> pricesService.getAll(pageable));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(pricesRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testUpdate_Success() {
	    Prices existingPrices = StaticPrices.prices1();
        Prices updatedPrices = StaticPrices.prices2();
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
	    when(pricesRepository.findById(StaticPrices.ID)).thenReturn(java.util.Optional.of(existingPrices));
        when(pricesRepository.save(updatedPrices)).thenReturn(updatedPrices);

        Prices result = pricesService.updateById(StaticPrices.ID, updatedPrices);

        assertEquals(updatedPrices, result);
        verify(pricesRepository, times(1)).findById(StaticPrices.ID);
        verify(pricesRepository, times(1)).save(updatedPrices);
    }

    @Test
    void testUpdateById_EntityNotFoundException_CarNotFound() {
        when(pricesRepository.findById(StaticPrices.ID)).thenReturn(java.util.Optional.of(prices));
        when(carService.getById(StaticCar.ID)).thenThrow(new EntityNotFoundException("Car not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> pricesService.updateById(StaticPrices.ID, prices));

        assertNotNull(exception);
        assertEquals("Car not found", exception.getMessage());
        verify(carService, times(1)).getById(StaticCar.ID);
    }


    @Test
    void testUpdateById_EntityNotFoundException() {
        Prices updatedPrices = StaticPrices.prices1();
        when(pricesRepository.findById(StaticPrices.ID)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> pricesService.updateById(StaticPrices.ID, updatedPrices));
        verify(pricesRepository, times(1)).findById(StaticPrices.ID);
        verify(pricesRepository, never()).save(updatedPrices);
    }

    @Test
    void testUpdateById_AnyException() {
        Prices existingPrices = StaticPrices.prices1();
        Prices updatedPrices = StaticPrices.prices2();
        when(pricesRepository.findById(StaticPrices.ID)).thenReturn(java.util.Optional.of(existingPrices));
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
	    when(pricesRepository.save(updatedPrices)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> pricesService.updateById(StaticPrices.ID, updatedPrices));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(pricesRepository, times(1)).save(updatedPrices);
    }

    @Test
    void testDeleteById_Success() {
        boolean result = pricesService.deleteById(StaticPrices.ID);

        verify(pricesRepository).deleteById(StaticPrices.ID);
        assertTrue(result);
    }

    @Test
    void testDeleteById_AnyException() {
        doThrow(new DataAccessException("Database connection failed") {}).when(pricesRepository).deleteById(StaticPrices.ID);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> pricesService.deleteById(StaticPrices.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(pricesRepository, times(1)).deleteById(StaticPrices.ID);
    }
}