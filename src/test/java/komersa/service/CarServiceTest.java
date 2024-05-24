package komersa.service;


import komersa.exception.EntityNotFoundException;
import komersa.model.Car;
import komersa.repository.CarRepository;
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

class CarServiceTest {

    @Mock
    private CarRepository carRepository;
    @InjectMocks
    private CarService carService;
    private final Car car = StaticCar.car1();
    private final Car car2 = StaticCar.car2();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
	    when(carRepository.save(any(Car.class))).thenReturn(car);

        Car createdCar = carService.create(car);

        assertNotNull(createdCar);
        assertEquals(car, createdCar);
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testCreate_DataAccessException() {
        when(carRepository.findById(StaticCar.ID)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> carService.getById(StaticCar.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(carRepository, times(1)).findById(StaticCar.ID);
    }

    @Test
    void testGetAll() {
        List<Car> carList = new ArrayList<>();
        carList.add(car);
        carList.add(car2);
        Page<Car> carPage = new PageImpl<>(carList);
        Pageable pageable = Pageable.unpaged();
        when(carRepository.findAll(pageable)).thenReturn(carPage);

        Page<Car> result = carService.getAll(pageable);

        assertEquals(carList.size(), result.getSize());
        assertEquals(car, result.getContent().get(0));
        assertEquals(car2, result.getContent().get(1));
    }

    @Test
    void testGetAll_AnyException() {
        when(carRepository.findAll(any(Pageable.class))).thenThrow(new DataAccessException("Database connection failed") {});

        Pageable pageable = Pageable.unpaged();
        RuntimeException exception = assertThrows(DataAccessException.class, () -> carService.getAll(pageable));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(carRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testUpdate_Success() {
	    Car existingCar = StaticCar.car1();
        Car updatedCar = StaticCar.car2();
	    when(carRepository.findById(StaticCar.ID)).thenReturn(java.util.Optional.of(existingCar));
        when(carRepository.save(updatedCar)).thenReturn(updatedCar);

        Car result = carService.updateById(StaticCar.ID, updatedCar);

        assertEquals(updatedCar, result);
        verify(carRepository, times(1)).findById(StaticCar.ID);
        verify(carRepository, times(1)).save(updatedCar);
    }


    @Test
    void testUpdateById_EntityNotFoundException() {
        Car updatedCar = StaticCar.car1();
        when(carRepository.findById(StaticCar.ID)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> carService.updateById(StaticCar.ID, updatedCar));
        verify(carRepository, times(1)).findById(StaticCar.ID);
        verify(carRepository, never()).save(updatedCar);
    }

    @Test
    void testUpdateById_AnyException() {
        Car existingCar = StaticCar.car1();
        Car updatedCar = StaticCar.car2();
        when(carRepository.findById(StaticCar.ID)).thenReturn(java.util.Optional.of(existingCar));
	    when(carRepository.save(updatedCar)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> carService.updateById(StaticCar.ID, updatedCar));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(carRepository, times(1)).save(updatedCar);
    }

    @Test
    void testDeleteById_Success() {
        boolean result = carService.deleteById(StaticCar.ID);

        verify(carRepository).deleteById(StaticCar.ID);
        assertTrue(result);
    }

    @Test
    void testDeleteById_AnyException() {
        doThrow(new DataAccessException("Database connection failed") {}).when(carRepository).deleteById(StaticCar.ID);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> carService.deleteById(StaticCar.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(carRepository, times(1)).deleteById(StaticCar.ID);
    }
}