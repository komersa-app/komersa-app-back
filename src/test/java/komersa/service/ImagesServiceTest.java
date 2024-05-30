package komersa.service;


import komersa.exception.EntityNotFoundException;
import komersa.model.Images;
import komersa.repository.ImagesRepository;
import komersa.staticObject.StaticImages;
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

class ImagesServiceTest {

    @Mock
    private ImagesRepository imagesRepository;
    @Mock
    private CarService carService;
    @InjectMocks
    private ImagesService imagesService;
    private final Images images = StaticImages.images1();
    private final Images images2 = StaticImages.images2();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate() {
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
	    when(imagesRepository.save(any(Images.class))).thenReturn(images);

        Images createdImages = imagesService.create(images);

        assertNotNull(createdImages);
        assertEquals(images, createdImages);
        verify(carService, times(1)).getById(StaticCar.ID);
        verify(imagesRepository, times(1)).save(images);
    }

    @Test
    void testCreate_EntityNotFoundException_CarNotFound() {
        when(carService.getById(StaticCar.ID)).thenThrow(new EntityNotFoundException("Car not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> imagesService.create(images));

        assertNotNull(exception);
        assertEquals("Car not found", exception.getMessage());
        verify(carService, times(1)).getById(StaticCar.ID);
        verifyNoInteractions(imagesRepository);
    }

    @Test
    void testCreate_DataAccessException() {
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
        when(imagesRepository.findById(StaticImages.ID)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> imagesService.getById(StaticImages.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(imagesRepository, times(1)).findById(StaticImages.ID);
    }

    @Test
    void testGetAll() {
        List<Images> imagesList = new ArrayList<>();
        imagesList.add(images);
        imagesList.add(images2);
        Page<Images> imagesPage = new PageImpl<>(imagesList);
        Pageable pageable = Pageable.unpaged();
        when(imagesRepository.findAll(pageable)).thenReturn(imagesPage);

        Page<Images> result = imagesService.getAll(pageable);

        assertEquals(imagesList.size(), result.getSize());
        assertEquals(images, result.getContent().get(0));
        assertEquals(images2, result.getContent().get(1));
    }

    @Test
    void testGetAll_AnyException() {
        when(imagesRepository.findAll(any(Pageable.class))).thenThrow(new DataAccessException("Database connection failed") {});

        Pageable pageable = Pageable.unpaged();
        RuntimeException exception = assertThrows(DataAccessException.class, () -> imagesService.getAll(pageable));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(imagesRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void testUpdate_Success() {
	    Images existingImages = StaticImages.images1();
        Images updatedImages = StaticImages.images2();
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
	    when(imagesRepository.findById(StaticImages.ID)).thenReturn(java.util.Optional.of(existingImages));
        when(imagesRepository.save(updatedImages)).thenReturn(updatedImages);

        Images result = imagesService.updateById(StaticImages.ID, updatedImages);

        assertEquals(updatedImages, result);
        verify(imagesRepository, times(1)).findById(StaticImages.ID);
        verify(imagesRepository, times(1)).save(updatedImages);
    }

    @Test
    void testUpdateById_EntityNotFoundException_CarNotFound() {
        when(imagesRepository.findById(StaticImages.ID)).thenReturn(java.util.Optional.of(images));
        when(carService.getById(StaticCar.ID)).thenThrow(new EntityNotFoundException("Car not found"));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> imagesService.updateById(StaticImages.ID, images));

        assertNotNull(exception);
        assertEquals("Car not found", exception.getMessage());
        verify(carService, times(1)).getById(StaticCar.ID);
    }


    @Test
    void testUpdateById_EntityNotFoundException() {
        Images updatedImages = StaticImages.images1();
        when(imagesRepository.findById(StaticImages.ID)).thenReturn(java.util.Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> imagesService.updateById(StaticImages.ID, updatedImages));
        verify(imagesRepository, times(1)).findById(StaticImages.ID);
        verify(imagesRepository, never()).save(updatedImages);
    }

    @Test
    void testUpdateById_AnyException() {
        Images existingImages = StaticImages.images1();
        Images updatedImages = StaticImages.images2();
        when(imagesRepository.findById(StaticImages.ID)).thenReturn(java.util.Optional.of(existingImages));
        when(carService.getById(StaticCar.ID)).thenReturn(StaticCar.car1());
	    when(imagesRepository.save(updatedImages)).thenThrow(new DataAccessException("Database connection failed") {
        });

        RuntimeException exception = assertThrows(RuntimeException.class, () -> imagesService.updateById(StaticImages.ID, updatedImages));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(imagesRepository, times(1)).save(updatedImages);
    }

    @Test
    void testDeleteById_Success() {
        boolean result = imagesService.deleteById(StaticImages.ID);

        verify(imagesRepository).deleteById(StaticImages.ID);
        assertTrue(result);
    }

    @Test
    void testDeleteById_AnyException() {
        doThrow(new DataAccessException("Database connection failed") {}).when(imagesRepository).deleteById(StaticImages.ID);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> imagesService.deleteById(StaticImages.ID));

        assertNotNull(exception);
        assertEquals("Database connection failed", exception.getMessage());
        verify(imagesRepository, times(1)).deleteById(StaticImages.ID);
    }
}