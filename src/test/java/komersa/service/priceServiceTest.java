package komersa.service;


import komersa.exception.EntityNotFoundException;
import komersa.model.price;
import komersa.repository.priceRepository;
import komersa.staticObject.Staticprice;
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

class priceServiceTest {

    @Mock
    private priceRepository priceRepository;
    @Mock
    private CarService carService;
    @InjectMocks
    private priceService priceService;
    private final price price = Staticprice.price1();
    private final price price2 = Staticprice.price2();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
	    when(priceRepository.save(any(price.class))).thenReturn(price);

        price createdprice = priceService.create(price);

        assertNotNull(createdprice);
        assertEquals(price, createdprice);
        verify(carService, times(1)).getById(StaticCar.ID);
        verify(priceRepository, times(1)).save(price);
    }

    @Test
    void testCreate_EntityNotFoundException_CarNotFound() {
        when(carService.getById(StaticCar.ID)).thenThrow(new EntityNotFoundException("Car not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> priceService.create(price));

        assertNotNull(exception);
        assertEquals("Car not found", exception.getMessage());
        verify(carService, times(1)).getById(StaticCar.ID);
        verifyNoInteractions(priceRepository);
    }

    @Test
    void testCreate_DataAccessException() {
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
        when(priceRepository.findById(Staticprice.ID)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> priceService.getById(Staticprice.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(priceRepository, times(1)).findById(Staticprice.ID);
    }

    @Test
    void testGetAll() {
        List<price> priceList = new ArrayList<>();
        priceList.add(price);
        priceList.add(price2);
        Page<price> pricePage = new PageImpl<>(priceList);
        Pageable pageable = Pageable.unpaged();
        when(priceRepository.findAll(pageable)).thenReturn(pricePage);

        Page<price> result = priceService.getAll(pageable);

        assertEquals(priceList.size(), result.getSize());
        assertEquals(price, result.getContent().get(0));
        assertEquals(price2, result.getContent().get(1));
    }

    @Test
    void testGetAll_AnyException() {
        when(priceRepository.findAll(any(Pageable.class))).thenThrow(new DataAccessException("Database connection failed") {});

        Pageable pageable = Pageable.unpaged();
        RuntimeException exception = assertThrows(DataAccessException.class, () -> priceService.getAll(pageable));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(priceRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testUpdate_Success() {
	    price existingprice = Staticprice.price1();
        price updatedprice = Staticprice.price2();
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
	    when(priceRepository.findById(Staticprice.ID)).thenReturn(java.util.Optional.of(existingprice));
        when(priceRepository.save(updatedprice)).thenReturn(updatedprice);

        price result = priceService.updateById(Staticprice.ID, updatedprice);

        assertEquals(updatedprice, result);
        verify(priceRepository, times(1)).findById(Staticprice.ID);
        verify(priceRepository, times(1)).save(updatedprice);
    }

    @Test
    void testUpdateById_EntityNotFoundException_CarNotFound() {
        when(priceRepository.findById(Staticprice.ID)).thenReturn(java.util.Optional.of(price));
        when(carService.getById(StaticCar.ID)).thenThrow(new EntityNotFoundException("Car not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> priceService.updateById(Staticprice.ID, price));

        assertNotNull(exception);
        assertEquals("Car not found", exception.getMessage());
        verify(carService, times(1)).getById(StaticCar.ID);
    }


    @Test
    void testUpdateById_EntityNotFoundException() {
        price updatedprice = Staticprice.price1();
        when(priceRepository.findById(Staticprice.ID)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> priceService.updateById(Staticprice.ID, updatedprice));
        verify(priceRepository, times(1)).findById(Staticprice.ID);
        verify(priceRepository, never()).save(updatedprice);
    }

    @Test
    void testUpdateById_AnyException() {
        price existingprice = Staticprice.price1();
        price updatedprice = Staticprice.price2();
        when(priceRepository.findById(Staticprice.ID)).thenReturn(java.util.Optional.of(existingprice));
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
	    when(priceRepository.save(updatedprice)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> priceService.updateById(Staticprice.ID, updatedprice));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(priceRepository, times(1)).save(updatedprice);
    }

    @Test
    void testDeleteById_Success() {
        boolean result = priceService.deleteById(Staticprice.ID);

        verify(priceRepository).deleteById(Staticprice.ID);
        assertTrue(result);
    }

    @Test
    void testDeleteById_AnyException() {
        doThrow(new DataAccessException("Database connection failed") {}).when(priceRepository).deleteById(Staticprice.ID);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> priceService.deleteById(Staticprice.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(priceRepository, times(1)).deleteById(Staticprice.ID);
    }
}